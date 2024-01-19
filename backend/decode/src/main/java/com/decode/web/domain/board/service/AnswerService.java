package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import java.util.List;

public interface AnswerService {

    public List<AnswerEntity> findAllByQuestion(QuestionEntity question);

    public Long saveAnswer(CreateAnswerDto createAnswerDto);

    public AnswerEntity updateAnswer(UpdateAnswerDto updateAnswerDto);

    public void deleteAnswer(String answerId);


}
