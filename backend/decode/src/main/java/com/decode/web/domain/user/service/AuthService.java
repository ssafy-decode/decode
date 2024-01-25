package com.decode.web.domain.user.service;

import com.decode.web.domain.user.dto.AuthDto;

public interface AuthService {

    AuthDto.TokenDto login(AuthDto.LoginDto loginDto);

    boolean validate(String token);

    boolean validateRefreshTokenInRedis(String token);

    // 이미 validate 메소드에서 토큰이 유효한지 검사했기 때문에 여기서는 토큰만 검사하면 된다.

    AuthDto.TokenDto generateToken(String provider, String email);

    void saveRefreshToken(String provider, String principal, String token);

    String getPrincipal(String token);

    String resolveToken(String token);

    void logout(String token);

}
