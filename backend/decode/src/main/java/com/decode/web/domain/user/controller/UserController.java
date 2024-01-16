package com.decode.web.domain.user.controller;

import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.global.ResponseDto;
import java.util.LinkedList;
import java.util.List;
import com.decode.web.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService us;
  private final UserMapper um;

  @Autowired
  UserController(UserService us, UserMapper um){
    this.us = us;
    this.um = um;
  }

  @GetMapping("/user")
  public ResponseDto getAllUser(){
    List<UserInfoDto> users = new LinkedList<>();
    for(UserInfoEntity u : us.getAllUser()){
      users.add(um.toDto(u));
    }
    return new ResponseDto().builder()
        .data(users)
        .status(HttpStatus.OK)
        .message("select All user info").build();
  }

}
