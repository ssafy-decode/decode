package com.decode.web.domain.oauth.controller;

import com.decode.web.domain.oauth.dto.GithubUserInfoDto;
import com.decode.web.domain.oauth.service.OAuth2Service;
import com.decode.web.domain.user.dto.AuthDto.TokenDto;
import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.service.PointService;
import com.decode.web.global.ResponseDto;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;
    private final PointService pointService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${domain.cookie.max-age}")
    private String COOKIE_MAX_AGE;

    @GetMapping("/auth/github")
    public ResponseDto githubLogin(@RequestParam("code") String code,
            HttpServletResponse response) {
        log.info("code: {}", code);

        // 1. https://github.com/login/oauth/access_token post 요청으로 client_id, client_secret, code 보내서 access token 받아오기
        String accessToken = oAuth2Service.getAccessToken(code);
        // 2. access token 이용해서 사용자 정보 받아오기
        GithubUserInfoDto userInfo = oAuth2Service.getUserInfo(accessToken);

        // 3. 사용자 정보 이용해서 회원가입 or 로그인 처리
        TokenDto tokenDto = oAuth2Service.login(userInfo, "github");

        pointService.updateUserPointAndExp(
                jwtTokenProvider.getAuthUserId(tokenDto.getAccessToken()), Point.LOGIN);

        // 4. 이후엔 기존 로그인과 똑같이 처리
        Cookie cookie = new Cookie("refresh-token", tokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Integer.parseInt(COOKIE_MAX_AGE));
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseDto.builder()
                .data(tokenDto.getAccessToken())
                .status(HttpStatus.OK)
                .message("깃헙 로그인 success")
                .build();
    }

}
