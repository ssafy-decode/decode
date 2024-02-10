package com.decode.web.domain.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private List<Long> userTagList;

    @Builder
    public ResponseUserProfileDto(Long id, int exp, int point, int coin, String nickname,
            String tier) {
        this.id = id;
        this.exp = exp;
        this.point = point;
        this.coin = coin;
        this.nickname = nickname;
        this.tier = tier;
    }

}
