package com.decode.web.domain.social.service;

import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import java.util.List;

public interface SocialService {

    void follow(Long fromUserId, Long toUserId);

    void followCancel(Long fromUserId, Long toUserId);

    List<ResponseUserProfileDto> getFollowers(Long userId);

    List<ResponseUserProfileDto> getFollowings(Long userId);

    boolean isFollow(Long fromUserId, Long toUserId);

}
