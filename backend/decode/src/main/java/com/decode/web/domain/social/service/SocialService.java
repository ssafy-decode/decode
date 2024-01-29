package com.decode.web.domain.social.service;

import com.decode.web.entity.UserProfileEntity;
import java.util.List;

public interface SocialService {
    void follow(Long fromUserId, Long toUserId);

    void followCancel(Long fromUserId, Long toUserId);

    List<UserProfileEntity> getFollowers(Long userId);

    List<UserProfileEntity> getFollowings(Long userId);

    boolean isFollow(Long fromUserId, Long toUserId);

}
