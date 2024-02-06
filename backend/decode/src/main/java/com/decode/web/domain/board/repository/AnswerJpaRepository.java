package com.decode.web.domain.board.repository;

import com.decode.web.entity.AnswerEntity;
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
                                + "order by a.question.createdTime desc "
                        , AnswerEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public AnswerEntity findOneByAnswerId(Long answerId) {
        return entityManager.createQuery("select distinct a from AnswerEntity a "
                                + "join fetch a.answerWriter "
                                + "join fetch a.question "
                                + "where a.id =:answerId "
                        , AnswerEntity.class)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }

    public Long getAnswerCountByUserId(Long userId) {
        return entityManager.createQuery("select count(a) from AnswerEntity a "
                                + "where a.answerWriter.id =: userId "
                        , Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
