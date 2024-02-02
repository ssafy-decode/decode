package com.decode.web.domain.oauth.dto;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class OAuthRequestDto {

    private String clientId;
    private String clientSecret;
    private String code;

}
