package com.decode.web.domain.social.repository;

import com.decode.web.entity.FollowEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {


    Optional<FollowEntity> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    List<FollowEntity> findByToUserId(Long userId);

    List<FollowEntity> findByFromUserId(Long userId);
}
