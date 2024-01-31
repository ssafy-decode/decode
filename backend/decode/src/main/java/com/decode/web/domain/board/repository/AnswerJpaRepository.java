package com.decode.web.domain.board.repository;

import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnswerJpaRepository {

    private final EntityManager entityManager;

    public List<AnswerEntity> findAllByUserId(Long userId) {
        return entityManager.createQuery("select distinct a from AnswerEntity a "
                        + "join fetch a.answerWriter "
                        + "join fetch a.question "
                        + "where a.answerWriter.id =:userId "
                        + "order by a.question.createdTime desc ", AnswerEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
