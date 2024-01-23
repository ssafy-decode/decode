package com.decode.web.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserProfileDto {

    private int exp;
    private int point;
    private int coin;
    private String nickname;
    private String tier;
    private String profileImg;

}
