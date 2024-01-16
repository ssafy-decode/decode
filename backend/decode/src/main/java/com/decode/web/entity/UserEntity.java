package com.decode.web.entity;
import com.decode.web.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "User")
@NoArgsConstructor


public class UserEntity extends CommonEntity {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_email", unique = true)
  private String email;

  @Column(name = "user_nickname", unique = true)
  private String nickname;

  @Column(name = "user_password")
  private String password;

  @Column(name = "user_point")
  private int point;

  @Column(name = "user_coin")
  private int coin;

  @Column(name = "user_birth")
  private String birth;

  @Column(name = "user_phone_number")
  private String phoneNumber;

  @Column(name = "user_exp")
  private int exp;

  @Column(name = "user_tier")
  private String tier;

  @Column(name = "user_profile_img")
  private String profileImg;

  @Column(name = "user_followers")
  private int followerNum;

  @Column(name = "user_followees")
  private int followeeNum;

  @Builder
  public UserEntity(Long id, String email, String nickname, String password, int point, int coin,
      String birth, String phoneNumber, int exp, String tier, String profileImg, int followerNum,
      int followeeNum) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.point = point;
    this.coin = coin;
    this.birth = birth;
    this.phoneNumber = phoneNumber;
    this.exp = exp;
    this.tier = tier;
    this.profileImg = profileImg;
    this.followerNum = followerNum;
    this.followeeNum = followeeNum;
  }
}
