package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import java.util.List;

public interface CommentService {

    Long save(CreateCommentDto commentEntity);

    List<CommentEntity> findByAnswer(AnswerEntity answerEntity);
//    List<CommentEntity> findByAnswerId(String answerId);

    CommentEntity update(UpdateCommentDto updateCommentDto);

    void delete(Long commentId);
}
