package com.decode.web.domain.oauth.service;

public interface OAuth2Service {
    String getAccessToken(String code);

    String getUserInfo(String accessToken);
}
