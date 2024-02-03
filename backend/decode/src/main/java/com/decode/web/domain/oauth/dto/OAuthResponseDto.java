package com.decode.web.domain.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class OAuthResponseDto {

    private String access_token;
    private String token_type;
    private String scope;
}
