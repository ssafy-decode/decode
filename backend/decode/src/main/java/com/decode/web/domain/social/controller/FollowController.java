package com.decode.web.domain.social.controller;

import com.decode.web.domain.social.service.SocialService;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/follow/{toUserId}")
    public ResponseDto follow(@PathVariable Long toUserId, Authentication auth) {
        socialService.follow((Long) auth.getPrincipal(), toUserId);
        return ResponseDto.builder()
                .message("팔로우 성공")
                .data("")
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/follow/{toUserId}")
    public ResponseDto followCancel(@PathVariable Long toUserId, Authentication auth) {
        socialService.followCancel((Long) auth.getPrincipal(), toUserId);
        return ResponseDto.builder()
                .message("팔로우 취소 성공")
                .data("")
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/followerlist/{userId}")
    public ResponseDto getFollowers(@PathVariable(name = "userId") Long userId) {
        return ResponseDto.builder()
                .data(socialService.getFollowers(userId))
                .message("팔로워 리스트 조회 성공")
                .status(HttpStatus.OK)
                .build();

    }

    @GetMapping("/followinglist/{userId}")
    public ResponseDto getFollowings(@PathVariable(name = "userId") Long userId) {
        return ResponseDto.builder()
                .data(socialService.getFollowings(userId))
                .message("팔로잉 리스트 조회 성공")
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/isfollow/{toUserId}")
    public ResponseDto isFollow(@PathVariable Long toUserId, Authentication auth) {
        return ResponseDto.builder()
                .data(socialService.isFollow((Long) auth.getPrincipal(), toUserId))
                .message("팔로우 여부 조회 성공")
                .status(HttpStatus.OK)
                .build();
    }
}
