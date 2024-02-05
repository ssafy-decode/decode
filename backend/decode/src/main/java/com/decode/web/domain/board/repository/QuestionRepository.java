package com.decode.web.domain.board.repository;

import com.decode.web.entity.QuestionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

}
