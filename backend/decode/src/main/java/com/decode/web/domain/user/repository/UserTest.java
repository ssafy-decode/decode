package com.decode.web.domain.user.repository;

import com.decode.web.domain.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;


@SpringBootTest
@Service
public class UserTest {
  @Autowired
  private UserRepository ur;

  @BeforeEach
  public void setUp() {
    ur.deleteAll();
  }
  @Test
  public void test(){
    String email = "ssafy@ssafy.com";
    String nickname = "ssafy";
    String password = "1234";
    int point = 0;
    int coin = 0;
    String birth = "1999-01-01";
    String phoneNumber = "010-1234-5678";
    int exp = 0;
    String tier = "bronze";
    String profileImg = "https://i.imgur.com/4H1kukF.png";
    int followerNum = 0;
    int followeeNum = 0;

    for(int i = 0 ; i < 100 ; i ++){
      ur.save(UserEntity.builder()
          .email(i+email)
          .nickname(nickname+i)
          .password(password)
          .point(point+i)
          .coin(coin+i)
          .birth(birth+i)
          .phoneNumber(phoneNumber+i)
          .exp(exp+i)
          .tier(tier+i)
          .profileImg(profileImg+i)
          .followerNum(followerNum+i)
          .followeeNum(followeeNum+i)
          .build()
      );
    }
//    ur.save(UserEntity.builder()
//        .email(email)
//        .nickname(nickname)
//        .password(password)
//        .point(point)
//        .coin(coin)
//        .birth(birth)
//        .phoneNumber(phoneNumber)
//        .exp(exp)
//        .tier(tier)
//        .profileImg(profileImg)
//        .followerNum(followerNum)
//        .followeeNum(followeeNum)
//        .build()
//    );

  }
}
