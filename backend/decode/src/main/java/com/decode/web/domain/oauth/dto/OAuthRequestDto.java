package com.decode.web.domain.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Getter
@NoArgsConstructor
public class OAuthRequestDto {

    private String client_id;
    private String client_secret;
    private String code;

}
