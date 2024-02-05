package com.decode.web.domain.oauth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GithubUserInfoDto {

    String login;
    String id;

}
