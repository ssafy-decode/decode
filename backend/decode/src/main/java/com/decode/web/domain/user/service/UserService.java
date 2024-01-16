package com.decode.web.domain.user.service;

import com.decode.web.domain.user.dto.UserDto;
import com.decode.web.domain.user.entity.UserEntity;
import java.util.List;

public interface UserService {

  public UserEntity getUserById(Long id);
  public List<UserEntity> getAllUser();
  public boolean emailDupCheck(String email);
  public boolean nickDupCheck(String nickname);

  public UserEntity getUserByEmail(String email);

  public boolean checkLogin();

}
