package com.decode.web.domain.social.service;

import com.decode.web.domain.social.repository.FollowRepository;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.FollowEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.entity.UserTagEntity;
import com.decode.web.exception.FollowException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialServiceImpl implements SocialService {

    private final FollowRepository followRepository;
    private final UserProfileRepository userProfileRepository;
    private final ResponseUserProfileMapper responseUserProfileMapper;


    @Override
    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        if (fromUserId.equals(toUserId)) {
            throw new FollowException("자기 자신을 팔로우 할 수 없습니다.");
        }
        if (followRepository.findByFromUserIdAndToUserId(fromUserId, toUserId).isPresent()) {
            throw new FollowException("이미 팔로우한 사용자입니다.");
        }
        UserProfileEntity fromUser = userProfileRepository.getReferenceById(fromUserId);
        UserProfileEntity toUser = userProfileRepository.getReferenceById(toUserId);
        FollowEntity followEntity = FollowEntity.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
        followRepository.save(followEntity);

    }

    @Override
    @Transactional
    public void followCancel(Long fromUserId, Long toUserId) {
        FollowEntity followEntity = followRepository.findByFromUserIdAndToUserId(fromUserId,
                        toUserId)
                .orElseThrow(() -> new FollowException("팔로우를 취소할 수 없습니다."));
        followRepository.delete(followEntity);
    }


    //userId 를 갖는 사용자의 팔로워 리스트 반환
    @Override
    public List<ResponseUserProfileDto> getFollowers(Long userId) {
        List<FollowEntity> followEntityList = followRepository.findByToUserId(userId);
        List<ResponseUserProfileDto> followerList = new LinkedList<>();
        for (FollowEntity followEntity : followEntityList) {
            UserProfileEntity following = userProfileRepository.findById(
                            followEntity.getFromUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "user not found with id: " + followEntity.getToUser().getId()));
            ResponseUserProfileDto userProfileDto = responseUserProfileMapper.toDto(following);
            userProfileDto.setUserTagList(
                    following.getUserTags().stream().map(UserTagEntity::getTagId).toList());
            followerList.add(userProfileDto);
        }
        return followerList;
    }

    //userId 를 갖는 사용자의 팔로잉 리스트 반환
    @Override
    public List<ResponseUserProfileDto> getFollowings(Long userId) {
        List<FollowEntity> followEntityList = followRepository.findByFromUserId(userId);
        List<ResponseUserProfileDto> followingList = new LinkedList<>();
        for (FollowEntity followEntity : followEntityList) {
            UserProfileEntity following = userProfileRepository.findById(
                            followEntity.getToUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "user not found with id: " + followEntity.getToUser().getId()));
            ResponseUserProfileDto userProfileDto = responseUserProfileMapper.toDto(following);
            userProfileDto.setUserTagList(
                    following.getUserTags().stream().map(UserTagEntity::getTagId).toList());
            followingList.add(userProfileDto);
        }
        return followingList;
    }

    @Override
    public boolean isFollow(Long fromUserId, Long toUserId) {
        return followRepository.findByFromUserIdAndToUserId(fromUserId, toUserId).isPresent();
    }

}
