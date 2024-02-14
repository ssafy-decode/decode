package com.decode.web.domain.oauth.service;

import com.decode.web.domain.oauth.dto.GithubUserInfoDto;
import com.decode.web.domain.oauth.dto.OAuthRequestDto;
import com.decode.web.domain.oauth.dto.OAuthResponseDto;
import com.decode.web.domain.user.dto.AuthDto.TokenDto;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.service.AuthService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.exception.CustomLoginException;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2ServiceImpl implements OAuth2Service {

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserInfoRepository userInfoRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Override
    public String getAccessToken(String code) {

        OAuthResponseDto result = restTemplate.postForEntity(
                        "https://github.com/login/oauth/access_token",
                        new OAuthRequestDto(clientId, clientSecret, code), OAuthResponseDto.class)
                .getBody();
        if (result == null) {
            throw new CustomLoginException("깃헙로그인 실패");
        }
        return result.getAccess_token();
    }

    @Override
    public GithubUserInfoDto getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return restTemplate.exchange("https://api.github.com/user",
                HttpMethod.GET, new HttpEntity<>(headers), GithubUserInfoDto.class).getBody();

    }

    @Override
    public TokenDto login(GithubUserInfoDto userInfo, String provider) {
        String email = userInfo.getLogin() + "@decode.com";

        // 최초 로그인이면 uid로 회원가입 처리
        if (userInfoRepository.findByEmail(email).isEmpty()) {
            UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                    .email(email)
                    .build();
            userService.createUser(userInfoEntity, userInfo.getId());
            log.info("최초 로그인 가입");
        }

        // jwt 발급
        String AccessToken = jwtTokenProvider.createAccessToken(email, provider);
        String RefreshToken = jwtTokenProvider.createRefreshToken(email, provider);

        // redis에 refresh token 저장
        authService.saveRefreshToken(provider, email, RefreshToken);
        userService.setAttendance(email);

        // 인증 정보 저장
        Authentication newAuthentication = jwtTokenProvider.getAuthentication(AccessToken);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return TokenDto.builder()
                .accessToken(AccessToken)
                .refreshToken(RefreshToken)
                .build();
    }

}
