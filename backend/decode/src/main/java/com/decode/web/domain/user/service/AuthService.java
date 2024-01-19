package com.decode.web.domain.user.service;

import com.decode.web.entity.UserInfoEntity;

public interface AuthService {
    String loginProcess(UserInfoEntity entity);
}
