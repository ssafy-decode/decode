package com.decode.web.domain.user.dto;

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
@Builder

public class UserInfoDto {

    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String birth;
    private String name;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


}
