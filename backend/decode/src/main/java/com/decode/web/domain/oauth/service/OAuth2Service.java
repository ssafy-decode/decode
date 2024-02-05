package com.decode.web.domain.oauth.service;

import com.decode.web.domain.oauth.dto.GithubUserInfoDto;
import com.decode.web.domain.user.dto.AuthDto.TokenDto;

public interface OAuth2Service {

    String getAccessToken(String code);

    GithubUserInfoDto getUserInfo(String accessToken);

    TokenDto login(GithubUserInfoDto userInfo, String provider);
}
