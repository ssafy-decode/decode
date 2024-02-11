package com.decode.web.domain.user.repository;

import com.decode.web.entity.UserProfileEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

    Optional<UserProfileEntity> findByNickname(String nickname);

    List<UserProfileEntity> findAllByOrderByExpDesc();

    Optional<UserProfileEntity> findById(Long userId);
}
