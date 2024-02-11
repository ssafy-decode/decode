package com.decode.web.global.utils.oauth2;

import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.service.AuthService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserInfoRepository userInfoRepository;
    private final AuthService authService;
    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // 최초 로그인 시 회원가입으로 이동, userInfo 말고 profile만 등록하게 jwt 토큰 발급
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String uid = oAuth2User.getName();
        log.info(oAuth2User.toString());
        log.info("oauth2 시작");
        String email = oAuth2User.getAttributes().get("login").toString() + "@decode.com";

        // 최초 로그인이면 github의 uid로 회원가입 처리
        if (userInfoRepository.findByEmail(email).isEmpty()) {
            UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                    .email(email)
                    .build();
            userService.createUser(userInfoEntity, uid);
            log.info("최초 로그인 가입");
        }

        // jwt 발급
        String AccessToken = jwtTokenProvider.createAccessToken(email, "github");
        String RefreshToken = jwtTokenProvider.createRefreshToken(email, "github");

        // redis에 refresh token 저장
        authService.saveRefreshToken("github", email, RefreshToken);

        // 인증 정보 저장
        Authentication newAuthentication = jwtTokenProvider.getAuthentication(AccessToken);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        //쿠키, 헤더 설정
        Cookie cookie = new Cookie("refresh-token", RefreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader("Authorization", "Bearer " + AccessToken);

        log.info("oauth 로그인 성공");

    }
}
