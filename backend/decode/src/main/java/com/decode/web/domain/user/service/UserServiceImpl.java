package com.decode.web.domain.user.service;

import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserServiceImpl(UserInfoRepository userInfoRepository,
            UserProfileRepository userProfileRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userProfileRepository = userProfileRepository;

    }

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
        if (profile.isPresent()) {
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
        if (password.length() < 8) {
            return false;
        } else if (!password.matches(".*[a-zA-Z].*")) {
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            return false;
        } else if (!password.matches(".*[~!@#$%^&*()].*")) {
            return false;
        }
        return true;
    }

    @Override
    public Long createUser(UserInfoEntity user) {
        if (emailDupCheck(user.getEmail()) && nickDupCheck(user.getNickname())
                && pwCheck(user.getPassword())) {
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
        return -1L;
    }

    @Override
    public Long createUser2(UserInfoEntity user) {
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
    public boolean checkLogin(String email, String password) {
        return false;
    }

    @Override
    public UserInfoEntity getUserByEmail(String email) {
        return null;
    }

}
