package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionDocument;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.ResponseQuestionListDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import java.util.List;

public interface QuestionService {

    List<ResponseQuestionListDto> searchQuestionByKeyword(String keywords, List<Long> tagIds);

    Long createQuestion(CreateQuestionDto question);

    ResponseQuestionDto questionDetail(Long questionId);

    void deleteQuestion(QuestionDocument questionDocument);

    ResponseQuestionDto updateQuestion(UpdateQuestionDto updateQuestion);

    BoardProfileResponseDto findAllByUserId(Long userId);
}
