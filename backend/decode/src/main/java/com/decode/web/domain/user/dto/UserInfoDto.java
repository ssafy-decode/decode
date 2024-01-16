package com.decode.web.domain.user.dto;

import java.time.LocalDateTime;
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

public class UserInfoDto {

  private Long id;
  private String email;
  private String nickname;
  private String password;
  private String phoneNumber;

  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;




}
