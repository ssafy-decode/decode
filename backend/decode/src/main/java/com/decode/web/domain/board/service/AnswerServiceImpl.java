package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {


    @Override
    public List<AnswerEntity> findAllByQuestion(QuestionEntity question) {

        return null;
    }

    @Override
    public Long saveAnswer(CreateAnswerDto createAnswerDto) {
        return null;
    }

    @Override
    public AnswerEntity updateAnswer(UpdateAnswerDto updateAnswerDto) {
        return null;
    }

    @Override
    public void deleteAnswer(String answerId) {

    }
}
