package com.decode.web.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "비밀번호는 8자리 이상, 영문자와 특수문자를 포함해야 합니다.", regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    private String password;
    @NotBlank(message = "폰번을 입력해주세요.")
    @Size(min = 4, max = 4, message = "폰번은 4자리입니다.")
    private String phoneNumber;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotBlank(message = "생년월일을 입력해주세요.")
    @Size(min = 6, max = 6, message = "생년월일은 6자리입니다.")
    private String birth;
    @NotBlank(message = "이름")
    private String name;

}
