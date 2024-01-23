package com.decode.web.domain.user.controller;

import com.decode.web.domain.user.dto.AuthDto.LoginDto;
import com.decode.web.domain.user.dto.AuthDto.TokenDto;
import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.domain.user.dto.UserRegistDto;
import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.domain.user.service.AuthService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.global.ResponseDto;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "UserController", description = "사용자 정보 관련 API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30일


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

    @GetMapping("/user/{id}")
    @Operation(summary = "사용자 정보 조회", description = "사용자 1명의 정보를 조회합니다.")
    public ResponseDto getUserById(@PathVariable Long id) {
        return new ResponseDto().builder()
                .data(userService.getUserById(id))
                .status(HttpStatus.OK)
                .message("select user info").build();
    }

    @PostMapping("/user/{id}")
    @Operation(summary = "사용자 정보 수정", description = "사용자 1명의 정보를 수정합니다.")
    // todo

    public ResponseDto updateUserById(@PathVariable Long id, @RequestBody UserInfoDto user) {
        return new ResponseDto().builder()
                .data(userService.getUserById(id))
                .status(HttpStatus.OK)
                .message("update user info").build();
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "사용자 프로필 조회", description = "사용자 프로필 정보를 조회합니다.")
    public ResponseDto getUserProfileById(@PathVariable Long id) {
        return new ResponseDto().builder()
                .data(userService.getUserProfileById(id))
                .status(HttpStatus.OK)
                .message("select user profile info").build();
    }

    @PostMapping("/regist")
    @Operation(summary = "회원 가입", description = "회원 가입 API")
    public ResponseDto createUser(@RequestBody UserRegistDto user) {
        log.debug("user : {}", user);
        Long id = null;
        if (userService.emailDupCheck(user.getEmail())
                && userService.nickDupCheck(user.getNickname())
                && userService.pwCheck(user.getPassword())) {

            user.setPassword(encoder.encode(user.getPassword()));
            UserInfoDto userInfoDto = UserInfoDto.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .birth(user.getBirth())
                    .build();

            id = userService.createUser(userMapper.toEntity(userInfoDto), user.getNickname());
        }
        if (id == null) {
            return new ResponseDto().builder()
                    .data(id)
                    .status(HttpStatus.BAD_REQUEST)
                    .message("create user").build();
        }
        return new ResponseDto().builder()
                .data(id)
                .status(HttpStatus.OK)
                .message("create user").build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 API")
    public ResponseDto login(@RequestBody LoginDto loginDto) {
        log.info("loginDto : {}", loginDto);
        TokenDto tokenDto = authService.login(loginDto);
        // 로그인 성공하면 토큰을 헤더에 쿠키로 저장

        HttpCookie httpcookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
                .httpOnly(true)
                .maxAge(COOKIE_MAX_AGE)
                .secure(true)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, httpcookie.toString());
        return new ResponseDto().builder()
                .data("Bearer " + tokenDto.getAccessToken())
                .status(HttpStatus.OK)
                .headers(headers)
                .message("login").build();
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API")
    public ResponseDto logout(@RequestHeader("Authorization") String token) {
        // 로그아웃 성공하면 쿠키 삭제 및 redis에서 토큰 삭제, status 200 반환,
        authService.logout(token);
        HttpCookie httpcookie = ResponseCookie.from("refresh-token", "")
                .httpOnly(true)
                .maxAge(0)
                .secure(true)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, httpcookie.toString());
        return new ResponseDto().builder()
                .data(null)
                .status(HttpStatus.OK)
                .headers(headers)
                .message("logout").build();

    }

    @PostMapping("/validate")
    @Operation(summary = "토큰 만료 검사", description = "토큰 만료 검사 API")
    public ResponseDto validate(@RequestHeader("Authorization") String token) {
//        요청 -> AT 검사 -> AT 유효 -> 요청 실행
//        요청 -> AT 검사 -> AT 기간만 만료 -> AT, RT로 재발급 요청 -> RT 유효 -> 재발급
//        요청 -> AT 검사 -> AT 기간만 만료 -> AT, RT로 재발급 요청 -> RT 유효X -> 로그아웃
        log.info("validate");
        // 만료되면 true, 유효하면 false
        if (!authService.validate(token)) {
            log.info("validated");
            return new ResponseDto().builder()
                    .data(null)
                    .status(HttpStatus.OK)
                    .message("validated").build();
        } else {
            log.info("expired");
            return new ResponseDto().builder()
                    .data(null)
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("expired").build();

        }
    }

    @GetMapping("/email")
    @Operation(summary = "이메일 중복 체크", description = "이메일 중복 체크 API")
    public ResponseDto emailDupCheck(@RequestParam String keyword) {
        return new ResponseDto().builder()
                .data(userService.emailDupCheck(keyword))
                .status(HttpStatus.OK)
                .message("email duplicate check").build();
    }

    @GetMapping("/nickname")
    @Operation(summary = "닉네임 중복 체크", description = "닉네임 중복 체크 API")
    public ResponseDto nickDupCheck(@RequestParam String keyword) {
        return new ResponseDto().builder()
                .data(userService.nickDupCheck(keyword))
                .status(HttpStatus.OK)
                .message("nickname duplicate check").build();
    }

    @PostMapping("/password-validation")
    @Operation(summary = "비밀번호 유효성 검사", description = "비밀번호 유효성 검사 API")
    public ResponseDto pwCheck(@RequestBody String password) {
        return new ResponseDto().builder()
                .data(userService.pwCheck(password))
                .status(HttpStatus.OK)
                .message("password validation check").build();
    }

    @PostMapping("/confirm")
    @Operation(summary = "비밀번호 확인", description = "비밀번호 확인 API")
    public ResponseDto pwConfirm(@RequestBody String password,
            @RequestHeader("Authorization") String token) {

        String encodedPassword = encoder.encode(password);
        Long uid = jwtTokenProvider.getAuthUserId(token);
        return new ResponseDto().builder()
                .data(userService.pwConfirm(uid, encodedPassword))
                .status(HttpStatus.OK)
                .message("password confirm").build();
    }


}
