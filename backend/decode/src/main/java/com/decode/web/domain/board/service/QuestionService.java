package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import com.decode.web.entity.QuestionEntity;
import java.util.List;

public interface QuestionService {

    List<QuestionListDto> searchQuestionByKeyword(String keyword, List<Long> tagIds);

    QuestionListDto convertQuestionEntityToQuestionListDto(QuestionEntity question);

    Long createQuestion(CreateQuestionDto question);

    ResponseQuestionDto questionDetail(Long questionId);

    void deleteQuestion(Long questionId, QuestionEntity targetQuestion);

    ResponseQuestionDto updateQuestion(UpdateQuestionDto updateQuestion);

    BoardProfileResponseDto findAllByUserId(Long userId);
}
