package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.GetQuestionListDto;
import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.service.QuestionService;
import com.decode.web.global.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @GetMapping("/question")
    public List<GetQuestionListDto> questionSearch(@RequestParam List<Integer> tags, @RequestParam String keyword){
        List<GetQuestionListDto> questions = questionService.questionSearch(tags, keyword);

        return questions;
    }

    @PostMapping("/question")
    public ResponseDto createQuestion(@RequestBody QuestionDto question){
        Long id = questionService.createQuestion(questionMapper.toEntity(question));
        return new ResponseDto();
    }
}
