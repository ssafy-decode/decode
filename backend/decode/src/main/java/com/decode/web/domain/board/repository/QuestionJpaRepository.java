package com.decode.web.domain.board.repository;

import com.decode.web.entity.QuestionEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionJpaRepository {

    private final EntityManager entityManager;

    public List<QuestionEntity> findAllByUserId(Long userId) {
        return entityManager.createQuery("select q from QuestionEntity q "
                        + "where q.questionWriter.id =:userId "
                        + "order by q.createdTime", QuestionEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
