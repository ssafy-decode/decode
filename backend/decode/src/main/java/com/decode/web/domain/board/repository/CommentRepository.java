package com.decode.web.domain.board.repository;

import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


    List<CommentEntity> findAllByAnswer(AnswerEntity answerEntity);

    CommentEntity findByCommentWriterIdAndId(Long commentWriterId, Long id);
}
