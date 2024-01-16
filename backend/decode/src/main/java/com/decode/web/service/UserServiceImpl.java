package com.decode.web.service;

import com.decode.web.dto.UserDto;
import com.decode.web.entity.UserEntity;
import com.decode.web.mapper.UserMapper;
import com.decode.web.repository.UserRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository ur;

  private final UserMapper um;

  @Autowired
  public UserServiceImpl(UserRepository ur, UserMapper um) {
    this.ur = ur;
    this.um = um;
  }

  @Override
  public UserDto getUserById(Long id) {
    Optional<UserEntity> user = ur.findById(id);
    if (user.isPresent()) {
      UserEntity entity = user.get();
      return um.toDto(entity);
    }
    return null;
  }

  @Override
  public List<UserDto> getAllUser() {
    List<UserDto> result = new LinkedList<>();
    List<UserEntity> users = ur.findAll();
    for (UserEntity user : users) {
      result.add(um.toDto(user));
    }
    return result;
  }

  @Override
  public boolean emailDupCheck(String email) {
    return false;
  }

  @Override
  public boolean nickDupCheck(String nickname) {
    return false;
  }

  @Override
  public UserDto getUserByEmail(String email) {
    return null;
  }

  @Override
  public boolean checkLogin() {
    return false;
  }
}
