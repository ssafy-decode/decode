package com.decode.web.domain.user.service;

import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserInfoRepository userInfoRepository;
    private final long expiredTime = 1000 * 60;
    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public AuthServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public String loginProcess(UserInfoEntity user) {
        return null;

    }
}
