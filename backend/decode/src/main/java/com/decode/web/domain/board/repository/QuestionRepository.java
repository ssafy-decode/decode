package com.decode.web.domain.board.repository;

import com.decode.web.entity.QuestionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    // 특정 키워드를 title에 포함한 게시글을 createTime이 최신인 순서로 조회
    List<QuestionEntity> findByTitleContainingOrderByCreatedTimeDesc(String keyword);

    List<QuestionEntity> findAllByOrderByCreatedTimeDesc();

    List<QuestionEntity> findAllByOrderByIdDesc();

    List<QuestionEntity> findByTitleContainingOrderByIdDesc(String keyword);
}
