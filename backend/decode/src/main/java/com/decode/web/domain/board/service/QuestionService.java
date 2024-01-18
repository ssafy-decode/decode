package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.GetQuestionListDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.entity.QuestionEntity;
import java.util.List;

public interface QuestionService {

    List<GetQuestionListDto> questionSearch(List<Integer> tags, String keyword);

    Long createQuestion(QuestionEntity question);
}
