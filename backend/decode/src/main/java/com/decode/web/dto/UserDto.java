package com.decode.web.dto;

import com.decode.web.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

  private Long id;
  private String email;
  private String nickname;
  private String password;
  private int point;
  private int coin;
  private String birth;
  private String phoneNumber;
  private int exp;
  private String tier;
  private LocalDateTime createdTime;
  private LocalDateTime updateTime;
  private String profileImg;
  private int followerNum;
  private int followeeNum;



}
