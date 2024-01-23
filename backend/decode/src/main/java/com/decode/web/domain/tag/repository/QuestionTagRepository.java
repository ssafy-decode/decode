package com.decode.web.domain.tag.repository;

import com.decode.web.entity.QuestionTagEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagRepository extends JpaRepository<QuestionTagEntity, Long> {

    List<QuestionTagEntity> findAllByQuestionId(Long id);

}
