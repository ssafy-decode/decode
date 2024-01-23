package com.decode.web.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseUserProfileDto {

    private Long id;
    private int exp;
    private int point;
    private int coin;
    private String nickname;
    private String tier;
    private String profileImg;
}
