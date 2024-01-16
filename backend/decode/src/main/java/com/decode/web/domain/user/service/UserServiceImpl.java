package com.decode.web.domain.user.service;

import com.decode.web.domain.user.mapper.UserMapper;
import com.decode.web.domain.user.entity.UserEntity;
import com.decode.web.domain.user.repository.UserRepository;
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
  public UserEntity getUserById(Long id) {
    Optional<UserEntity> user = ur.findById(id);
    if (user.isPresent()) {
      return user.get();
    }
    return null;
  }

  @Override
  public List<UserEntity> getAllUser() {

    return ur.findAll();
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
  public UserEntity getUserByEmail(String email) {
    return null;
  }

  @Override
  public boolean checkLogin() {
    return false;
  }
}
