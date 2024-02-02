package com.decode.web.domain.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OAuthResponseDto {

    private String accessToken;
    private String tokenType;
    private String scope;
}
