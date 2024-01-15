package com.decode.web.service;

import com.decode.web.dto.UserDto;
import com.decode.web.entity.UserEntity;
import com.decode.web.repository.UserRepository;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository ur;

  @Autowired
  public UserServiceImpl(UserRepository ur) {
    this.ur = ur;
  }

  @Override
  public UserDto getUserById(Long id) {
    return null;
  }

  @Override
  public List<UserDto> getAllUser() {
    List<UserDto> result = new LinkedList<>();
    List<UserEntity> users = ur.findAll();
    for (UserEntity user : users) {
      result.add(new UserDto(user.getId(), user.getEmail(), user.getNickname(), user.getPassword(),
          user.getPoint(), user.getCoin(), user.getBirth(), user.getPhoneNumber(), user.getExp(),
          user.getTier(), user.getCreatedTime(), user.getUpdatedTime(), user.getProfileImg(),
          user.getFollowerNum(), user.getFolloweeNum()));
    }
    return result;
  }
}
