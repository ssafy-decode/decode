package com.decode.web.domain.board.repository;

import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.RecommendEntity;
import com.decode.web.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<RecommendEntity, Long> {

    RecommendEntity findByAnswerAndUserProfile(AnswerEntity answer, UserProfileEntity userProfile);
}
