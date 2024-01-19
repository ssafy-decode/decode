package com.decode.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "UserProfile")
@NoArgsConstructor
public class UserProfileEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_exp")
    private int exp;

    @Column(name = "user_tier")
    private String tier;

    @Column(name = "user_profile_img")
    private String profileImg;

    @Column(name = "user_point")
    private int point;

    @Column(name = "user_coin")
    private int coin;

    @Builder
    public UserProfileEntity(Long id, int exp, String tier, String profileImg, int point,
                             int coin) {
        this.id = id;
        this.exp = exp;
        this.tier = tier;
        this.profileImg = profileImg;
        this.point = point;
        this.coin = coin;
    }
}
