package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.domain.board.dto.ResponseCommentDto;
import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.CommentEntity;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface CommentService {

    Long save(CreateCommentDto commentEntity);

    CommentEntity update(UpdateCommentDto updateCommentDto);

    void delete(Long userId, Long commentId) throws BadRequestException;

    List<ResponseCommentDto> getResponseAnswerDtoList(AnswerEntity answerEntity);

    ResponseCommentDto convertToResponseCommentDto(CommentEntity commentEntity);
}
