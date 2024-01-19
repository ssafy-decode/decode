package com.decode.web.domain.user.controller;

import com.decode.web.domain.user.dto.AuthDto;
import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.domain.user.service.AuthService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UserController", description = "사용자 정보 관련 API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;




    @Deprecated
    @GetMapping("/user")
    @Operation(summary = "사용자 정보 조회", description = "모든 사용자 정보를 조회합니다.")
    public ResponseDto getAllUser() {
        List<UserInfoDto> users = new LinkedList<>();
        for (UserInfoEntity u : userService.getAllUser()) {
            users.add(userMapper.toDto(u));
        }
        return new ResponseDto().builder()
                .data(users)
                .status(HttpStatus.OK)
                .message("select All user info").build();
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "사용자 프로필 조회", description = "사용자 프로필 정보를 조회합니다.")
    public ResponseDto getUserProfileById(Long id) {
        return new ResponseDto().builder()
                .data(userService.getUserProfileById(id))
                .status(HttpStatus.OK)
                .message("select user profile info").build();
    }

    @PostMapping("/regist")
    @Operation(summary = "회원 가입", description = "회원 가입 API")
    public ResponseDto createUser(@RequestBody UserInfoDto user) {
        Long id;
        if(userService.emailDupCheck(user.getEmail()) && userService.nickDupCheck(user.getNickname()) && userService.pwCheck(user.getPassword())){
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            id = userService.createUser(userMapper.toEntity(user));
            user.setId(id);
            return new ResponseDto().builder()
                    .data(user)
                    .status(HttpStatus.OK)
                    .message("create user info").build();
        }
        return new ResponseDto().builder()
                .data(user)
                .status(HttpStatus.BAD_REQUEST)
                .message("가입 실패").build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 API")
    public ResponseDto login(@RequestBody AuthDto.LoginDto loginDto) {

        // 로그인 성공하면 토큰을 헤더에 쿠키로 저장


        // 실패하면 토큰은 반환 안되니까 null로 들어가고, status는 400으로 반환
        return null;
    }
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API")
    public ResponseDto logout(@RequestHeader("Authorization") String token)  {
        // 로그아웃 성공하면 쿠키 삭제 및 redis에서 토큰 삭제, status 200 반환
        return null;
    }
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    public ResponseDto reissue(@CookieValue(name = "refresh-token") String refreshToken,
                               @RequestHeader("Authorization") String accessToken) {
        // 토큰 재발급 성공하면 토큰을 헤더에 쿠키로 저장
        return null;
    }



}
