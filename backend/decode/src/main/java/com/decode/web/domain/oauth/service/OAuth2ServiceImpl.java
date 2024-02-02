package com.decode.web.domain.oauth.service;

import com.decode.web.domain.oauth.dto.OAuthRequestDto;
import com.decode.web.domain.oauth.dto.OAuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2ServiceImpl implements OAuth2Service {

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;
    private RestTemplate restTemplate;

    @Override
    public String getAccessToken(String code) {
        return restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                new OAuthRequestDto(clientId, clientSecret, code),
                OAuthResponseDto.class
        ).getAccessToken();
    }

    @Override
    public String getUserInfo(String accessToken) {
        return null;
    }

}
