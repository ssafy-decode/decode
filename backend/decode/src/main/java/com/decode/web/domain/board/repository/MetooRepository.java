package com.decode.web.domain.board.repository;

import com.decode.web.entity.MetooEntity;
import com.decode.web.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetooRepository extends JpaRepository<MetooEntity, Long> {
    Long countAllByQuestion(QuestionEntity question);
}
