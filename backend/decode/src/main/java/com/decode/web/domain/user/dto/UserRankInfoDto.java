package com.decode.web.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRankInfoDto {

    private Long userId;
    private String nickname;
    private String tier;
    private String profileImg;
    private int exp;
    private int totalUserCount;
    private int myRank;
    private int answerCount;
    private int adoptCount;
    private int followerCount;
    private int followCount;
}

