package com.decode.web.domain.user.service;

import com.decode.web.domain.tag.repository.UserTagRepository;
import com.decode.web.domain.user.dto.RequestUserTagDto;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.entity.UserTagEntity;
import java.util.List;
import java.util.Optional;
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

    @Override
    public UserInfoEntity getUserById(Long id) {
        Optional<UserInfoEntity> user = userInfoRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
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
    public Long createUser(UserInfoEntity user, String nickname) {

        UserProfileEntity profile = UserProfileEntity.builder()
                .nickname(nickname)
                .exp(0)
                .tier("bronze")
                .profileImg("default")
                .point(0)
                .coin(0)
                .build();

        profile.setUserInfoEntity(user);
        userProfileRepository.save(profile);
        return user.getId();
    }

    @Override
    public UserInfoEntity getUserByEmail(String email) throws UsernameNotFoundException {
        UserInfoEntity user = userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        log.info("user: {}", user);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public void updateUserInfo(Long id, String password) {
        UserInfoEntity user = userInfoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        user.updateInfo(password);
        userInfoRepository.save(user);
    }


    @Override
    public void updateUserProfile(Long id, UserProfileEntity profile) {
        UserProfileEntity user = userProfileRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        user.updateProfile(profile);
        userProfileRepository.save(user);
    }

    @Override
    public boolean pwConfirm(Long id, String password) {
        UserInfoEntity user = userInfoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        if (user.getPassword().equals(password)) {
            return false;
        }

        return true;
    }

    @Override
    public String findEmail(String name, String phoneNumber, String birth) {
        UserInfoEntity user = userInfoRepository.findByNameAndPhoneNumberAndBirth(name, phoneNumber,
                        birth)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 찾기 실패"));
        return user.getEmail();
    }

    @Override
    public String findPassword(String email, String name, String phoneNumber, String birth) {
        return null;
    }

    @Override
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

}
