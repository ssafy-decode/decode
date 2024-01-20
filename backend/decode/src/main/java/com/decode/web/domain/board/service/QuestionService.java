package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.InputQuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import java.util.List;

public interface QuestionService {

    List<QuestionListDto> searchQuestionByKeyword(String keyword);

    String createQuestion(InputQuestionDto question);
}
