package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.GetQuestionListDto;
import com.decode.web.domain.board.dto.InputQuestionDto;
import com.decode.web.domain.board.mapper.QuestionMapper;
import com.decode.web.domain.board.service.QuestionService;
import com.decode.web.global.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping("/question")
    public List<GetQuestionListDto> questionSearch(@RequestParam List<Integer> tags,
            @RequestParam String keyword) {
        List<GetQuestionListDto> questions = questionService.questionSearch(tags, keyword);

        return questions;
    }

    @PostMapping("/question")
    public ResponseDto createQuestion(@RequestBody InputQuestionDto question) {
        String title = questionService.createQuestion(question);

        return ResponseDto.builder().status(HttpStatus.OK).message(title + "질문 등록 성공").build();
    }
}
