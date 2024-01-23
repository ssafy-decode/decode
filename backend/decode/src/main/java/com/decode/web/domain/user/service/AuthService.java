package com.decode.web.domain.user.service;

import com.decode.web.domain.user.dto.AuthDto;

public interface AuthService {

    AuthDto.TokenDto login(AuthDto.LoginDto loginDto);

    boolean validate(String token);

    AuthDto.TokenDto reissue(String accessToken);

    AuthDto.TokenDto generateToken(String provider, String email);

    void saveRefreshToken(String provider, String principal, String token);

    String getPrincipal(String token);

    String resolveToken(String token);

    void logout(String token);

}
