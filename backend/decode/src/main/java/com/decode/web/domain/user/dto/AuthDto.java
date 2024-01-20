package com.decode.web.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class AuthDto {
    @Getter
    @NoArgsConstructor
    @ToString

    public static class LoginDto {
        private String email;
        private String password;

        @Builder
        public LoginDto(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TokenDto {
        private String accessToken;
        private String refreshToken;

        @Builder
        public TokenDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

}
