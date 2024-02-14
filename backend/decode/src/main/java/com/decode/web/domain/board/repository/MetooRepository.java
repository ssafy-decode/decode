package com.decode.web.domain.board.repository;

import com.decode.web.entity.MetooEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetooRepository extends JpaRepository<MetooEntity, Long> {

    MetooEntity findByUserProfileIdAndId(Long userProfileId, Long meTooId);

    MetooEntity findByQuestionIdAndUserProfileId(Long questionId, Long userProfileId);

    List<MetooEntity> findAllByUserProfileId(Long userProfileId);
}
