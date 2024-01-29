package com.decode.web.entity;

import com.decode.web.exception.NotEnoughCoinException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
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

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @Setter
    private UserInfoEntity userInfoEntity;

    @Id
    @Column(name = "user_id")
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

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<FollowEntity> followings;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<FollowEntity> followers;


    @Builder
    public UserProfileEntity(String nickname, int exp, String tier, String profileImg, int point,
            int coin) {
        this.id = userInfoEntity.getId();
        this.nickname = nickname;
        this.exp = exp;
        this.tier = tier;
        this.profileImg = profileImg;
        this.point = point;
        this.coin = coin;
    }

    public void updateProfile(UserProfileEntity profile) {
        this.nickname = profile.getNickname();
        this.exp = profile.getExp();
        this.tier = profile.getTier();
        this.profileImg = profile.getProfileImg();
        this.point = profile.getPoint();
        this.coin = profile.getCoin();
    }

    public void decreaseCoin(int amount) {
        if (amount > this.coin) {
            throw new NotEnoughCoinException("amount = " + amount + " coin = " + coin);
        }
        this.coin -= amount;
    }
}
