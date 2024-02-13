package com.decode.web.domain.user.service;

import com.decode.web.domain.user.dto.FindPasswordDto;
import com.decode.web.domain.user.dto.RankResponseDto;
import com.decode.web.domain.user.dto.RequestUserTagDto;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.dto.UserRankInfoDto;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    Long createUser(UserInfoEntity user, String nickname);

    UserInfoEntity getUserByEmail(String email) throws UsernameNotFoundException;

    // 회원 정보 update, False : 실패, True : 성공
    void updateUserInfo(Long id, String password);

    void updateUserProfile(Long id, UserProfileEntity profile);

    boolean pwConfirm(Long id, String password);

    String findEmail(String name, String phoneNumber, String birth);

    boolean findUserByEmailAndNameAndPhoneNumberAndBirth(FindPasswordDto findPasswordDto);


    void addUserTag(RequestUserTagDto requestUserTagDto);

    void updateUserTag(RequestUserTagDto requestUserTagDto);

    void setAttendance(String email);

    Set<String> getAttendance(Long id);

//    void setExp(Long id, int exp);

    Map<String, Integer> getExp(Long id);

    List<UserProfileEntity> getRank();

    ResponseUserProfileDto getUserProfileDtoById(Long id);

    List<RankResponseDto> getRankV2();

    UserRankInfoDto getRankByUserId(Long userId);
}
