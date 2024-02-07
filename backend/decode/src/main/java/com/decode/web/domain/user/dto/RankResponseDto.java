package com.decode.web.domain.user.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankResponseDto {

    private Long userId; // userId
    private String nickname;
    private String tier;
    private String profileImg;
    private int exp;
    private int answerCount;
    private int adoptCount;
    private int followerCount;
    private List<Long> tagIds;
}
