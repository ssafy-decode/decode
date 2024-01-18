package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.GetQuestionListDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.entity.QuestionEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<GetQuestionListDto> questionSearch(List<Integer> tags, String keyword) {
        return null;
    }

    @Override
    public Long createQuestion(QuestionEntity question) {
        questionRepository.save(question);
        return question.getId();
    }
}
