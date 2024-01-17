package com.decode.web.domain.user.service;

import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import java.util.List;

public interface UserService {

    // 한 유저의 개인 정보 조회 ( 이메일, 닉네임, 비밀번호, 가입일, 폰번 )
    UserInfoEntity getUserById(Long id);

    // 한 유저의 프로필 정보 조회 ( 경험치, 코인, 티어, 포인트, 프사 경로 )
    UserProfileEntity getUserProfileById(Long id);

    List<UserInfoEntity> getAllUser();

    // 이메일 중복 검사, False : 중복, True : 중복 아님
    boolean emailDupCheck(String email);

    // 닉네임 중복 검사, False : 중복, True : 중복 아님
    boolean nickDupCheck(String nickname);

    // PW 유효성, False : 유효하지 않음, True : 유효
    boolean pwCheck(String password);

    // 회원 정보 create, False : 실패, True : 성공
    // 1: api 호출 시 백엔드에서도 email, nick, pw 검사 후 회원가입
    // 2: api 호출 시 검증 없이 바로 가입
    boolean createUser(UserInfoEntity user);

    boolean createUser2(UserInfoEntity user);







    boolean checkLogin(String email, String password);

    UserInfoEntity getUserByEmail(String email);


}
