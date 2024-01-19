package com.decode.web.domain.user.service;

import com.decode.web.domain.user.dto.AuthDto;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final String SERVER = "Server";

    @Override
    @Transactional
    public AuthDto.TokenDto login(AuthDto.LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return generateToken(SERVER, authentication.getName());
    }

    @Override
    public boolean validate(String token) {
        String requestAccessToken = resolveToken(token);
        return jwtTokenProvider.validateTokenExpired(requestAccessToken);
    }

    @Override
    @Transactional
    public AuthDto.TokenDto reissue(String accessToken, String refreshToken) {
        String requestAccessToken = resolveToken(accessToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(requestAccessToken);
        String principal = getPrincipal(requestAccessToken);

        String refreshTokenInRedis = redisService.getValues("RT(" + SERVER + "):" + principal);
        if (refreshTokenInRedis == null) { // Redis에 저장되어 있는 RT가 없을 경우
            return null; // -> 재로그인 요청
        }

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
        if(!jwtTokenProvider.validateToken(refreshToken) || !refreshTokenInRedis.equals(refreshToken)){
            redisService.deleteValues("RT(" + SERVER + "):" + principal);
            return null; // -> 재로그인 요청
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 토큰 재발급 및 Redis 업데이트
        redisService.deleteValues("RT(" + SERVER + "):" + principal); // 기존 RT 삭제
        AuthDto.TokenDto tokenDto = AuthDto.TokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(principal))
                .refreshToken(jwtTokenProvider.createRefreshToken(principal))
                .build();
        saveRefreshToken(SERVER, principal, tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Override
    @Transactional
    public AuthDto.TokenDto generateToken(String provider, String email) {
        if(redisService.getValues("RT("+provider+"):"+email) != null){
            redisService.deleteValues("RT("+provider+"):"+email);
        }

        AuthDto.TokenDto tokenDto = AuthDto.TokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(email))
                .refreshToken(jwtTokenProvider.createRefreshToken(email))
                .build();
        saveRefreshToken(provider, email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Override
    @Transactional
    public void saveRefreshToken(String provider, String principal, String token) {
        redisService.setValuesWithTimeout("RT(:"+provider+"):"+principal,
                token,
                jwtTokenProvider.getTokenExpirationTime(token));
    }

    @Override
    public String getPrincipal(String token) {
        return jwtTokenProvider.getAuthentication(token).getName();
    }

    @Override
    public String resolveToken(String token) {
        if(token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    @Override
    @Transactional
    public void logout(String token) {
        String requestAccessToken = resolveToken(token);
        String principal = getPrincipal(requestAccessToken);

        String refreshTokenInRedis = redisService.getValues("RT(:"+SERVER+"):"+principal);
        if (refreshTokenInRedis != null){
            redisService.deleteValues("RT(:"+SERVER+"):"+principal);
        }
        long expiration = jwtTokenProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
        redisService.setValuesWithTimeout(requestAccessToken,"logout",expiration);
    }
}
