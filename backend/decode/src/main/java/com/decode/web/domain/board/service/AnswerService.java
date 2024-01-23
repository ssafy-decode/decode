package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.RecommendDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import java.util.List;

public interface AnswerService {

    List<AnswerEntity> findAllByQuestion(QuestionEntity question);

    Long save(CreateAnswerDto createAnswerDto);

    AnswerEntity update(UpdateAnswerDto updateAnswerDto);

    void delete(Long answerId);

    List<ResponseAnswerDto> getResponseAnswerDtoList(QuestionEntity questionEntity);
    ResponseAnswerDto convertToResponseAnswerDto(AnswerEntity answerEntity);
    Long recommend(Long answerId, RecommendDto recommendDto);
}
