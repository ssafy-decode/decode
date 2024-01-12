package com.decode.web.dto;

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
public class UserDto {

  private int userId;
  private String email;
  private String nickname;
  private String password;
  private int point;
  private int coin;
  private String birth;
  private String phoneNumber;
  private int exp;
  private String tier;
  private String createdTime;
  private String updateTime;
  private String profileImg;
  private int followerNum;
  private int followeeNum;

}
