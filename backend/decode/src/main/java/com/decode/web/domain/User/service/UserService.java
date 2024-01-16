package com.decode.web.domain.User.service;

import com.decode.web.domain.User.dto.UserDto;
import java.util.List;

public interface UserService {

  public UserDto getUserById(Long id);
  public List<UserDto> getAllUser();
  public boolean emailDupCheck(String email);
  public boolean nickDupCheck(String nickname);

  public UserDto getUserByEmail(String email);

  public boolean checkLogin();

}
