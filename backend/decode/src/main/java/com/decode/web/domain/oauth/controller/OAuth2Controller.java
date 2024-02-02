package com.decode.web.domain.oauth.controller;

import com.decode.web.domain.oauth.service.OAuth2Service;
import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;


    @GetMapping("/auth/github")
    public ResponseDto githubLogin(@RequestParam("code") String code) {
        log.info("code: {}", code);

        // 1. https://github.com/login/oauth/access_token post 요청으로 client_id, client_secret, code 보내서 access token 받아오기
        // 2. access token 이용해서 사용자 정보 받아오기
        // 3. 사용자 정보 이용해서 회원가입 or 로그인 처리
        // 4. 로그인 처리 후에는 jwt 토큰 발급해서 리턴
        String accessToken = oAuth2Service.getAccessToken(code);
        log.info("accessToken: {}", accessToken);




        return ResponseDto.builder()
                .data(accessToken)
                .build();
    }

}
