package com.decode.web.domain.user.service;

import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public AuthServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    private final long expiredTime = 1000 * 60;

    @Override
    public String loginProcess(UserInfoEntity user) {
        String result = null;
        String email = user.getEmail();
        Optional<UserInfoEntity> userInfo = userInfoRepository.findByEmail(email);
        if (userInfo.isPresent()){

        }

    }
}
