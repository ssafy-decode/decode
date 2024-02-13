package com.decode.web.domain.user.service;


import com.decode.web.domain.common.redis.RedisService;
import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.UserProfileEntity;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserProfileRepository userProfileRepository;
    private final RedisService redisService;

    @Transactional
    public void updateUserPointAndExp(Long userId, Point type) {
        UserProfileEntity profileEntity = userProfileRepository.findById(userId)
                .get();
        LocalDate date = LocalDate.now();
        String email = profileEntity.getUserInfoEntity().getEmail();
        String key = "EXP:" + email;
        String hashKey = date.toString();
        redisService.incrementValueForHash(key, hashKey, type.exp());
        profileEntity.updateUserPointAndExp(type);
    }
}
