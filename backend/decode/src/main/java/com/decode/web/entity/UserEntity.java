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


@Getter
@Entity
@Table(name = "User")
@Builder
@AllArgsConstructor


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

  public UserDto toDto(){
    return UserDto.builder()
        .id(id)
        .email(email)
        .nickname(nickname)
        .password(password)
        .point(point)
        .coin(coin)
        .birth(birth)
        .phoneNumber(phoneNumber)
        .exp(exp)
        .tier(tier)
        .profileImg(profileImg)
        .followerNum(followerNum)
        .followeeNum(followeeNum)
        .build();
  }


  public UserEntity() {

  }
}
