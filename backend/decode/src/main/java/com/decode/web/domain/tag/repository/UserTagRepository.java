package com.decode.web.domain.tag.repository;

import com.decode.web.entity.UserProfileEntity;
import com.decode.web.entity.UserTagEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTagEntity, Long> {

    List<UserTagEntity> findAllByUserProfile(UserProfileEntity userProfile);

    List<UserTagEntity> findAllByUserProfileId(Long userProfileId);
}
