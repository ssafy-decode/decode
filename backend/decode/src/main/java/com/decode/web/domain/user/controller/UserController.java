package com.decode.web.domain.user.controller;

import com.decode.web.domain.user.dto.AuthDto;
import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.domain.user.service.AuthService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.global.ResponseDto;
import java.util.LinkedList;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Tag(name = "UserController", description = "사용자 정보 관련 API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @Autowired
    UserController(UserService userService, UserMapper userMapper,AuthService authService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authService = authService;
    }

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
        Long id = userService.createUser(userMapper.toEntity(user));
        if (id != -1) {
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
    public ResponseDto login(@RequestBody AuthDto.LoginDto loginDto){

        // 로그인 성공하면 토큰을 ResponseDto에 담아서 반환


        // 실패하면 실패 메시지를 ResponseDto에 담아서 반환
        return null;
    }

}
