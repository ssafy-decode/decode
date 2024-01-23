package com.decode.web.domain.user.dto;

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
public class UserRegistDto {

    private String email;
    private String password;
    private String phoneNumber;
    private String nickname;
    private String birth;
    private String name;

}
