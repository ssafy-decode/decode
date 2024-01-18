package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import java.util.List;

public interface CommentService {

    Long save(CommentEntity commentEntity);

    List<CommentEntity> findByAnswer(AnswerEntity answerEntity);

    CommentEntity update(UpdateCommentDto updateCommentDto);

    void delete(Long commentId);
}
