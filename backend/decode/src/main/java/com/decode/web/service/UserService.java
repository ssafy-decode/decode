package com.decode.web.service;

import com.decode.web.dto.UserDto;
import com.decode.web.entity.UserEntity;
import java.util.List;

public interface UserService {

  public UserDto getUserById(Long id);
  public List<UserDto> getAllUser();
  public boolean emailDupCheck(String email);
  public boolean nickDupCheck(String nickname);

  public UserDto getUserByEmail(String email);

  public boolean checkLogin();

}
