package com.decode.web.domain.User.controller;

import java.util.List;
import com.decode.web.domain.User.dto.UserDto;
import com.decode.web.domain.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private UserService us;

  @Autowired
  UserController(UserService us){
    this.us = us;
  }

  @GetMapping("/user")
  public List<UserDto> user(){
    return us.getAllUser();
  }

}
