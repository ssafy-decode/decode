package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.InputQuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.service.QuestionService;
import com.decode.web.global.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseDto questionSearch(@RequestParam String keyword) {
        List<QuestionListDto> questionList = questionService.searchQuestionByKeyword(keyword);
        return ResponseDto.builder().status(HttpStatus.OK).message("조회 완료").data(questionList)
                .build();
    }

    @PostMapping
    public ResponseDto createQuestion(@RequestBody InputQuestionDto question) {
        String title = questionService.createQuestion(question);

        return ResponseDto.builder().status(HttpStatus.OK).message(title + "질문 등록 성공").build();
    }

    @GetMapping("/{questionId}")
    public ResponseDto questionDetail(@PathVariable Long questionId) {
        ResponseQuestionDto question = questionService.questionDetail(questionId);
        return ResponseDto.builder().status(HttpStatus.OK).message((questionId + "조회"))
                .data(question).build();
    }

    @DeleteMapping
    public ResponseDto deleteQuestion(@PathVariable Long questionId){
//        questionService.deleteQuestion(questionId);
        return ResponseDto.builder().status(HttpStatus.OK).build();
    }

}
