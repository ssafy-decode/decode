package com.decode.web.domain.user.service;

import com.decode.web.domain.common.redis.RedisService;
import com.decode.web.domain.user.dto.AuthDto;
import com.decode.web.domain.user.dto.AuthDto.TokenDto;
import com.decode.web.exception.CustomLoginException;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final UserService userService;
    private final String SERVER = "Server";

    @Override
    @Transactional
    public AuthDto.TokenDto login(AuthDto.LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword());
        try {
            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);

            TokenDto tokenDto = generateToken(SERVER, authentication.getName());
            userService.setAttendance(loginDto.getEmail());
            return tokenDto;
        } catch (BadCredentialsException e) {
            throw new CustomLoginException("아이디/비밀번호가 일치하지 않아요.");
        }
    }

    @Override
    public boolean validate(String token) {
        String requestAccessToken = resolveToken(token);
        return jwtTokenProvider.validateToken(requestAccessToken);

    }

    @Override
    @Transactional
    public boolean validateRefreshTokenInRedis(String token) {

        String principal = jwtTokenProvider.getPrincipal(token);
        String provider = jwtTokenProvider.getProvider(token);
        String refreshTokenInRedis = redisService.getValues("RT:" + provider + ":" + principal);
        if (refreshTokenInRedis == null) {
            return false;
        }
        return jwtTokenProvider.validateToken(refreshTokenInRedis);
    }

    @Override
    @Transactional
    public AuthDto.TokenDto generateToken(String provider, String email) {
        if (redisService.getValues("RT:" + provider + ":" + email) != null) {
            redisService.deleteValues("RT:" + provider + ":" + email);
        }

        AuthDto.TokenDto tokenDto = AuthDto.TokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(email, provider))
                .refreshToken(jwtTokenProvider.createRefreshToken(email, provider))
                .build();
        saveRefreshToken(provider, email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    @Override
    @Transactional
    public void saveRefreshToken(String provider, String principal, String token) {
        redisService.setValuesWithTimeout("RT:" + provider + ":" + principal,
                token,
                jwtTokenProvider.getTokenExpirationTime(token));
    }


    @Override
    public String getPrincipal(String token) {
        return jwtTokenProvider.getAuthentication(token).getName();
    }

    @Override
    public String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    @Override
    @Transactional
    public void logout(String token) {

        String requestAccessToken = resolveToken(token);
        if(requestAccessToken == null){
            throw new CustomLoginException("알 수 없는 오류");
        }

        String principal = jwtTokenProvider.getPrincipal(requestAccessToken);
        String provider = jwtTokenProvider.getProvider(requestAccessToken);

        String refreshTokenInRedis = redisService.getValues("RT:" + provider + ":" + principal);
        if (refreshTokenInRedis != null) {
            redisService.deleteValues("RT:" + provider + ":" + principal);
        }

    }
}
