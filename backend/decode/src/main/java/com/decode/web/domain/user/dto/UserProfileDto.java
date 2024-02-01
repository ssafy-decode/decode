package com.decode.web.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
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
public class UserProfileDto {

    private Long id;

    @NotBlank
    private int exp;

    @NotBlank
    private int point;

    @NotBlank
    private int coin;

    @NotBlank
    private String nickname;

    @NotBlank
    private String tier;

    private String profileImg;

}
