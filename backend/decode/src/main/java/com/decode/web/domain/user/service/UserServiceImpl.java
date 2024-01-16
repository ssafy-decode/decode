package com.decode.web.domain.user.service;

import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.domain.user.repository.UserInfoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserInfoRepository uir;
  @Autowired
  public UserServiceImpl(UserInfoRepository ur) {
    this.uir = ur;

  }

  @Override
  public UserInfoEntity getUserById(Long id) {
    Optional<UserInfoEntity> user = uir.findById(id);
    if (user.isPresent()) {
      return user.get();
    }
    return null;
  }

  @Override
  public UserProfileEntity getUserProfileById(Long id) {
    return null;
  }

  @Override
  public List<UserInfoEntity> getAllUser() {

    return uir.findAll();
  }

  @Override
  public boolean emailDupCheck(String email) {
    return uir.findByEmail(email).isEmpty();
  }

  @Override
  public boolean nickDupCheck(String nickname) {
    return uir.findByNickname(nickname).isEmpty();
  }

  @Override
  public boolean checkLogin(String email, String password) {
    return false;
  }

  @Override
  public UserInfoEntity getUserByEmail(String email) {
    return null;
  }

}
