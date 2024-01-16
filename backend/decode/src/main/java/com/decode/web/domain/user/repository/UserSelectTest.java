package com.decode.web.domain.user.repository;

import com.decode.web.entity.UserInfoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;


@SpringBootTest
@Service
public class UserSelectTest {
  @Autowired
  private UserInfoRepository ur;

  @BeforeEach
  public void setUp() {
    ur.deleteAll();
  }
  @Test
  public void test(){
    String email = "ssafy@ssafy.com";
    String nickname = "ssafy";
    String password = "1234";
    String phoneNumber = "010-1234-5678";

    for(int i = 0 ; i < 100 ; i ++){
      ur.save(UserInfoEntity.builder()
          .email(i+email)
          .nickname(nickname+i)
          .password(password)
          .phoneNumber(phoneNumber+i)
          .build()
      );
    }
  }
}
