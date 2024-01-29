package com.decode.web.domain.social.controller;

import com.decode.web.domain.social.service.SocialService;
import com.decode.web.domain.user.mapper.UserProfileMapper;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Follow", description = "팔로우 관련 API")
public class FollowController {

    private final SocialService socialService;
    private final UserProfileMapper userProfileMapper;

    @PostMapping("/follow/{toUserId}")
    public ResponseDto follow(@PathVariable Long toUserId, Authentication auth) {
        socialService.follow((Long) auth.getPrincipal(), toUserId);
        return ResponseDto.builder()
                .message("팔로우 성공")
                .build();
    }

    @DeleteMapping("/follow/{toUserId}")
    public ResponseDto followCancel(@PathVariable Long toUserId, Authentication auth) {
        socialService.followCancel((Long) auth.getPrincipal(), toUserId);
        return ResponseDto.builder()
                .message("팔로우 취소 성공")
                .build();
    }

    @GetMapping("/followerlist/{userId}")
    public ResponseDto getFollowers(@PathVariable Long userId   ) {
        return ResponseDto.builder()
                .data(userProfileMapper.toDto(socialService.getFollowers(userId)))
                .message("팔로워 리스트 조회 성공")
                .build();

    }

    @GetMapping("/followinglist/{userId}")
    public ResponseDto getFollowings(@PathVariable Long userId) {
        return ResponseDto.builder()
                .data(userProfileMapper.toDto(socialService.getFollowings(userId)))
                .message("팔로잉 리스트 조회 성공")
                .build();
    }


}
