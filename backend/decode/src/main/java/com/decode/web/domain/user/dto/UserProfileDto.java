package com.decode.web.domain.user.dto;

import lombok.*;

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
