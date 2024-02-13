package com.decode.web.domain.user.controller;

import com.decode.web.domain.mail.dto.MailDto;
import com.decode.web.domain.mail.service.MailService;
import com.decode.web.domain.user.dto.AuthDto.LoginDto;
import com.decode.web.domain.user.dto.AuthDto.TokenDto;
import com.decode.web.domain.user.dto.FindEmailDto;
import com.decode.web.domain.user.dto.FindPasswordDto;
import com.decode.web.domain.user.dto.InfoUpdateDto;
import com.decode.web.domain.user.dto.RankResponseDto;
import com.decode.web.domain.user.dto.RequestUserTagDto;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.domain.user.dto.UserRankInfoDto;
import com.decode.web.domain.user.dto.UserRegistDto;
import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.domain.user.mapper.UserProfileMapper;
import com.decode.web.domain.user.service.AuthService;
import com.decode.web.domain.user.service.PointService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.global.ResponseDto;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final UserProfileMapper userProfileMapper;
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PointService pointService;


    @Value("${domain.cookie.max-age}")
    private String COOKIE_MAX_AGE;

    @Value("${domain.url}")
    private String DOMAIN_URL;

    @Deprecated
    @GetMapping("/user")
    @Operation(summary = "사용자 정보 조회", description = "모든 사용자 정보를 조회합니다.")
    public ResponseDto getAllUser() {
        List<UserInfoDto> users = new LinkedList<>();
        for (UserInfoEntity u : userService.getAllUser()) {
            users.add(userMapper.toDto(u));
        }
        return ResponseDto.builder()
                .data(users)
                .status(HttpStatus.OK)
                .message("select All user info").build();
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "사용자 정보 조회", description = "사용자 1명의 정보를 조회합니다.")
    public ResponseDto getUserById(@PathVariable Long id) {
        return ResponseDto.builder()
                .data(userMapper.toDto(userService.getUserById(id)))
                .status(HttpStatus.OK)
                .message("select user info").build();
    }

    @GetMapping("/user/profile/{userId}")
    @Operation(summary = "UserProfile 조회", description = "userId에 해당하는 유저의 프로필 조회")
    public ResponseDto getUserProfile(@PathVariable Long userId) {
        ResponseUserProfileDto userProfile = userService.getUserProfileDtoById(userId);
        return ResponseDto.builder().data(userProfile).status(HttpStatus.OK).build();
    }

    @PostMapping("/user")
    @Operation(summary = "사용자 정보 수정", description = "사용자 1명의 정보를 수정합니다.(비밀번호 변경)")
    public ResponseDto updateUserById(@Valid @RequestBody InfoUpdateDto infoUpdateDto,
            Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        String password = infoUpdateDto.getPassword();
        boolean result = false;
        if (!userService.pwCheck(password)) {
            throw new IllegalArgumentException("비밀번호는 8자리 이상, 영문자와 특수문자를 포함해야 합니다.");
        }
        if (userId != null) {
            userService.updateUserInfo(userId, encoder.encode(password));
            result = true;
        }
        return ResponseDto.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("update user info")
                .build();
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "사용자 프로필 조회", description = "사용자 프로필 정보를 조회합니다.")
    public ResponseDto getUserProfileById(@PathVariable Long id) {
        return ResponseDto.builder()
                .data(userProfileMapper.toDto(userService.getUserProfileById(id)))
                .status(HttpStatus.OK)
                .message("select user profile info")
                .build();
    }

//    @GetMapping("/info")
//    @Operation(summary = "로그인한 사용자 조회", description = "현재 사용자 정보를 조회합니다.")
//    public ResponseDto getUserProfileNow() {
//        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return ResponseDto.builder()
//                .data(userProfileMapper.toDto(userService.getUserProfileById(userId)))
//                .status(HttpStatus.OK)
//                .message("get user info").build();
//    }


    @PostMapping("/regist")
    @Operation(summary = "회원 가입", description = "회원 가입 API")
    public ResponseDto createUser(@Valid @RequestBody UserRegistDto user) {
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
        return ResponseDto.builder()
                .data(id)
                .status(HttpStatus.OK)
                .message("create user")
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 API")
    public ResponseDto login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse res) {
        log.info("loginDto : {}", loginDto);
        TokenDto tokenDto = authService.login(loginDto);
        // 로그인 성공하면 액세스토큰은 헤더, 리프레시토큰은 쿠키에 저장
        Cookie cookie = new Cookie("refresh-token", tokenDto.getRefreshToken());
        cookie.setMaxAge(Integer.parseInt(COOKIE_MAX_AGE));
        cookie.setSecure(true);
        cookie.setDomain(DOMAIN_URL);
        res.addCookie(cookie);

        res.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        pointService.updateUserPointAndExp(
                jwtTokenProvider.getAuthUserId(tokenDto.getAccessToken()), Point.LOGIN);
        return ResponseDto.builder()
                .data(jwtTokenProvider.getAuthUserId(tokenDto.getAccessToken()))
                .status(HttpStatus.OK)
                .message("login")
                .build();
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API")
    public ResponseDto logout(@RequestHeader("Authorization") String token,
            HttpServletResponse res) {
        // 로그아웃 성공하면 쿠키 삭제 및 redis에서 토큰 삭제
        authService.logout(token);

        //쿠키 삭제
        Cookie cookie = new Cookie("refresh-token", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setDomain("i10a507.p.ssafy.io");
        res.addCookie(cookie);

        // 헤더에서 토큰 삭제
        res.setHeader("Authorization", null);

        return ResponseDto.builder()
                .data("")
                .status(HttpStatus.OK)
                .message("logout")
                .build();

    }

    @GetMapping("/email")
    @Operation(summary = "이메일 중복 체크", description = "이메일 중복 체크 API")
    public ResponseDto emailDupCheck(@RequestParam String keyword) {
        boolean result = false;
        if (keyword != null) {
            result = userService.emailDupCheck(keyword);
        }
        return ResponseDto.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("email duplicate check")
                .build();
    }

    @GetMapping("/nickname")
    @Operation(summary = "닉네임 중복 체크", description = "닉네임 중복 체크 API")
    public ResponseDto nickDupCheck(@RequestParam String keyword) {
        boolean result = false;
        if (keyword != null) {
            result = userService.nickDupCheck(keyword);
        }
        return ResponseDto.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("nickname duplicate check")
                .build();
    }

    @PostMapping("/password-validation")
    @Operation(summary = "비밀번호 유효성 검사", description = "비밀번호 유효성 검사 API")
    public ResponseDto pwCheck(@RequestBody String password) {
        return ResponseDto.builder()
                .data(userService.pwCheck(password))
                .status(HttpStatus.OK)
                .message("password validation check")
                .build();
    }

    @PostMapping("/confirm")
    @Operation(summary = "비밀번호 확인", description = "비밀번호 확인 API")
    public ResponseDto pwConfirm(@RequestBody Map<String, String> map) {

        boolean result = false;

        String password = map.get("password");
        String encodedPassword = SecurityContextHolder.getContext().getAuthentication()
                .getCredentials().toString();
        if (encoder.matches(password, encodedPassword)) {
            result = true;
        }
        return ResponseDto.builder()
                .data(result)
                .status(HttpStatus.OK)
                .message("password confirm")
                .build();
    }

    @PostMapping("/profile/{id}")
    @Operation(summary = "프로필 수정", description = "프로필 수정 API")
    public ResponseDto updateUserProfile(@PathVariable Long id,
            @RequestBody UserProfileDto userProfileDto) {
        userService.updateUserProfile(id, userProfileMapper.toEntity(userProfileDto));
        return ResponseDto.builder()
                .data("")
                .status(HttpStatus.OK)
                .message("update user profile")
                .build();
    }

    @PostMapping("/email")
    @Operation(summary = "이메일 찾기", description = "이메일 찾기 API")
    public ResponseDto findEmail(@Valid @RequestBody FindEmailDto findEmailDto) {
        return ResponseDto.builder()
                .data(userService.findEmail(findEmailDto.getName(), findEmailDto.getPhoneNumber(),
                        findEmailDto.getBirth()))
                .status(HttpStatus.OK)
                .message("find email")
                .build();
    }


    @PostMapping("/addUserTag")
    @Operation(summary = "유저 태그 선택", description = "신규 유저의 선호 기술 태그 추가")
    public ResponseDto addUserTag(@RequestBody
            RequestUserTagDto requestUserTagDto) {
        userService.addUserTag(requestUserTagDto);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data("")
                .message("기술 태그 추가 완료")
                .build();
    }

    @PatchMapping("/updateUserTag")
    @Operation(summary = "유저 태그 수정", description = "기존 유저의 선후 기술 태그 수정")
    public ResponseDto updateUserTag(@RequestBody RequestUserTagDto requestUserTagDto,
            Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        if (!userId.equals(requestUserTagDto.getUserId())) {
            return ResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("사용자 불일치").build();
        }
        userService.updateUserTag(requestUserTagDto);
        return ResponseDto.builder()
                .data("")
                .message("기술 태그 수정 완료")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/password")
    @Operation(summary = "비밀번호 찾기", description = "비밀번호 찾기 API")
    public ResponseDto findPassword(@Valid @RequestBody FindPasswordDto findPasswordDto) {
        // email로 유저 info를 조회했을 때, name, phoneNumber, birth가 일치하는 유저가 있으면
        if (userService.findUserByEmailAndNameAndPhoneNumberAndBirth(findPasswordDto)) {
            // 임시 비밀번호 생성
            String rawPasswrod = findPasswordDto.getBirth() + "decode!";
            String tempPassword = encoder.encode(rawPasswrod);
            // 해당 유저의 비밀번호를 임시 비밀번호로 변경하고
            userService.updateUserInfo(
                    userService.getUserByEmail(findPasswordDto.getEmail()).getId(),
                    tempPassword);
            // 해당 이메일로 임시 비밀번호 발송.
            MailDto mailDto = MailDto.builder()
                    .to(findPasswordDto.getEmail())
                    .title("Decode 임시 비밀번호 발급")
                    .message("임시 비밀번호는 생년월일(6자리) + decode! 입니다.")
                    .build();
            log.info("mailDto : {}", mailDto);
            mailService.sendMail(mailDto);
        }
        return ResponseDto.builder()
                .data("")
                .status(HttpStatus.OK)
                .message("임시 비밀번호 발급")
                .build();
    }

    @GetMapping("/attendance/{id}")
    @Operation(summary = "출석 로그 확인", description = "출석 로그 확인 API")
    public ResponseDto getAttendance(@PathVariable Long id) {
        return ResponseDto.builder()
                .data(userService.getAttendance(id))
                .status(HttpStatus.OK)
                .message("출석 로그 확인")
                .build();
    }

    @GetMapping("/exp/{id}")
    @Operation(summary = "경험치 로그", description = "경험치로그 API")
    public ResponseDto getExp(@PathVariable Long id) {
        return ResponseDto.builder()
                .data(userService.getExp(id))
                .status(HttpStatus.OK)
                .message("경험치 로그")
                .build();
    }

    @GetMapping("/rank")
    @Operation(summary = "랭킹 V2", description = "랭킹 API V2")
    public ResponseDto getRankV2() {
        List<RankResponseDto> data = userService.getRankV2();
        return ResponseDto.builder()
                .data(data)
                .status(HttpStatus.OK)
                .message("랭킹")
                .build();
    }

    @GetMapping("/rank/{userId}")
    @Operation(summary = "유저아이디로 랭킹 조회", description = "유저아이디로 랭킹 조회")
    public ResponseDto getRankByUserId(@PathVariable Long userId) {
        UserRankInfoDto data = userService.getRankByUserId(userId);
        return ResponseDto.builder()
                .data(data)
                .status(HttpStatus.OK)
                .message("랭킹")
                .build();
    }

}
