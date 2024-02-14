package com.decode.web.domain.board.repository;

import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    List<AnswerEntity> findByIsAdoptedTrueAndQuestionId(Long questionId);

    List<AnswerEntity> findAllByQuestion(QuestionEntity question);

    @Query("select a from AnswerEntity a where a.id in (:questionIdList) and a.isAdopted = true ")
    List<AnswerEntity> findByQuestionId(@Param("questionIdList") List<Long> questionIdList);


}
