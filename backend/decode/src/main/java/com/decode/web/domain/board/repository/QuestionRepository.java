package com.decode.web.domain.board.repository;

import com.decode.web.entity.QuestionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findAllByOrderByIdDesc();
}
