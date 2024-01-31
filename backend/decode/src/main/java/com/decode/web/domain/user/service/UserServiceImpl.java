package com.decode.web.domain.user.service;

import com.decode.web.domain.common.redis.RedisService;
import com.decode.web.domain.tag.repository.UserTagRepository;
import com.decode.web.domain.user.dto.FindPasswordDto;
import com.decode.web.domain.user.dto.RequestUserTagDto;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.entity.UserTagEntity;
import com.decode.web.exception.UserException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserTagRepository userTagRepository;
    private final RedisService redisService;

    @Override
    public UserInfoEntity getUserById(Long id) {
        UserInfoEntity user = userInfoRepository.getReferenceById(id);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public UserProfileEntity getUserProfileById(Long id) {
        Optional<UserProfileEntity> profile = userProfileRepository.findById(id);
        log.info("id: {}", id);
        if (profile.isPresent()) {
            log.info("profile: {}", profile.get());
            return profile.get();
        }
        return null;
    }

    @Override
    public List<UserInfoEntity> getAllUser() {

        return userInfoRepository.findAll();
    }

    @Override
    public boolean emailDupCheck(String email) {
        return userInfoRepository.findByEmail(email).isEmpty();
    }

    @Override
    public boolean nickDupCheck(String nickname) {
        return userProfileRepository.findByNickname(nickname).isEmpty();
    }

    @Override
    public boolean pwCheck(String password) {
        // 영어/숫자/특수문자 조합으로 8자리 이상
        if (password.length() < 8
                || !password.matches(".*[a-zA-Z].*")
                || !password.matches(".*[0-9].*")
                || !password.matches(".*[~!@#$%^&*()_+|<>?:{}].*")) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public Long createUser(UserInfoEntity user, String nickname) {

        UserProfileEntity profile = UserProfileEntity.builder()
                .nickname(nickname)
                .exp(0)
                .tier("bronze")
                .profileImg("default")
                .point(5000)
                .coin(0)
                .build();

        profile.setUserInfoEntity(user);
        userProfileRepository.save(profile);
        if(user.getId() == null){
            throw new UserException("회원가입 실패");
        }
        return user.getId();
    }

    @Override
    public UserInfoEntity getUserByEmail(String email) throws UsernameNotFoundException {
        UserInfoEntity user = userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return user;
    }

    @Override
    @Transactional
    public void updateUserInfo(Long id, String password) {
        UserInfoEntity user = userInfoRepository.getReferenceById(id);
        user.updateInfo(password);

    }


    @Override
    @Transactional
    public void updateUserProfile(Long id, UserProfileEntity profile) {
        UserProfileEntity user = userProfileRepository.getReferenceById(id);
        user.updateProfile(profile);
    }

    @Override
    public boolean pwConfirm(Long id, String password) {
        UserInfoEntity user = userInfoRepository.getReferenceById(id);
        log.info("user: {}", user.getPassword());
        log.info("password: {}", password);
        return user.getPassword().equals(password);
    }

    @Override
    public String findEmail(String name, String phoneNumber, String birth) {
        UserInfoEntity user = userInfoRepository.findByNameAndPhoneNumberAndBirth(name, phoneNumber,
                        birth)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 찾기 실패"));
        return user.getEmail();
    }

    @Override
    public boolean findUserByEmailAndNameAndPhoneNumberAndBirth(FindPasswordDto findPasswordDto) {
        return userInfoRepository.findByEmailAndNameAndPhoneNumberAndBirth(
                findPasswordDto.getEmail(),
                findPasswordDto.getName(),
                findPasswordDto.getPhoneNumber(),
                findPasswordDto.getBirth()
        ).isPresent();
    }


    @Override
    @Transactional
    public void addUserTag(RequestUserTagDto requestUserTagDto) {
        Long userId = requestUserTagDto.getUserId();
        List<Long> tagIds = requestUserTagDto.getTagIdList();
        for (Long tagId : tagIds) {
            userTagRepository.save(UserTagEntity.builder()
                    .userProfile(userProfileRepository.getReferenceById(userId)).tagId(tagId)
                    .build());
        }


    }

    @Override
    @Transactional
    public void updateUserTag(RequestUserTagDto requestUserTagDto) {
        Long userId = requestUserTagDto.getUserId();
        List<Long> tagIds = requestUserTagDto.getTagIdList();
        List<UserTagEntity> userTagEntities = userTagRepository.findAllByUserProfile(
                userProfileRepository.getReferenceById(userId));
        userTagRepository.deleteAll(userTagEntities);
        for (Long tagId : tagIds) {
            userTagRepository.save(UserTagEntity.builder()
                    .userProfile(userProfileRepository.getReferenceById(userId)).tagId(tagId)
                    .build());
        }

    }

    @Override
    public void setAttendance(String email) {
        LocalDate date = LocalDate.now();
        String key = "ATD:" + email;
        String value = date.toString();
        redisService.setValueForSet(key, value);
    }

    @Override
    public Set<String> getAttendance(Long id) {
        String key = "ATD:" + userInfoRepository.getReferenceById(id).getEmail();
        return redisService.getValuesForSet(key);
    }

//    public void

}
