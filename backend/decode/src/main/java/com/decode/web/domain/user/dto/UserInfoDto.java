package com.decode.web.domain.user.dto;

import lombok.*;

import java.time.LocalDateTime;

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
