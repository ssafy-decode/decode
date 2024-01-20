package com.decode.web.domain.user.service;

import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final UserProfileRepository userProfileRepository;

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
        return userInfoRepository.findByNickname(nickname).isEmpty();
    }

    @Override
    public boolean pwCheck(String password) {
        // 영어/숫자/특수문자 조합으로 8자리 이상
        if (password.length() < 8
                || !password.matches(".*[a-zA-Z].*")
                || !password.matches(".*[0-9].*")
                || !password.matches(".*[~!@#$%^&*()_+|<>?:{}].*"))
            return false;

        return true;
    }

    @Override
    public Long createUser(UserInfoEntity user) {
        userInfoRepository.save(user);
        userProfileRepository.save(UserProfileEntity.builder()
                .id(user.getId())
                .exp(0)
                .tier("bronze")
                .profileImg(null)
                .point(0)
                .coin(0)
                .build());
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
    public Object pwConfirm(Long id, String password) {
        UserInfoEntity user = userInfoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        if (user.getPassword().equals(password)) {
            return false;
        }

        return true;
    }

}
