package com.decode.web.domain.user.service;


import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.UserProfileEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void updateUserPointAndExp(Long userId, Point type) {
        UserProfileEntity profileEntity = userProfileRepository.findById(userId)
                .get();
        profileEntity.updateUserPointAndExp(type);
    }
}
