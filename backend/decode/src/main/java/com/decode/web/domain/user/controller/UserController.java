package com.decode.web.domain.user.controller;

import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.global.ResponseDto;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService us;
    private final UserMapper um;

    @Autowired
    UserController(UserService us, UserMapper um) {
        this.us = us;
        this.um = um;
    }

    @Deprecated
    @GetMapping("/user")
    public ResponseDto getAllUser() {
        List<UserInfoDto> users = new LinkedList<>();
        for (UserInfoEntity u : us.getAllUser()) {
            users.add(um.toDto(u));
        }
        return new ResponseDto().builder()
                .data(users)
                .status(HttpStatus.OK)
                .message("select All user info").build();
    }

    @GetMapping("/profile/{id}")
    public ResponseDto getUserProfileById(Long id) {
        return new ResponseDto().builder()
                .data(us.getUserProfileById(id))
                .status(HttpStatus.OK)
                .message("select user profile info").build();
    }

    @PostMapping("/regist")
    public ResponseDto createUser(@RequestBody UserInfoDto user) {
        if (us.createUser(um.toEntity(user))) {
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

}
