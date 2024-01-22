package com.decode.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "UserProfile")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    @Column(name = "user_nickname", unique = true)
    private String nickname;

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
    public UserProfileEntity(Long id, String nickname, int exp, String tier, String profileImg, int point, int coin) {
        this.id = id;
        this.nickname = nickname;
        this.exp = exp;
        this.tier = tier;
        this.profileImg = profileImg;
        this.point = point;
        this.coin = coin;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @Setter
    @JoinColumn(name = "user_id")
    private UserInfoEntity userInfo;

}
