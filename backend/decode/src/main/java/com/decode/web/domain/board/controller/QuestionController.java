package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import com.decode.web.domain.board.service.QuestionService;
import com.decode.web.global.ResponseDto;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseDto questionSearch(@RequestParam String keyword, @RequestParam List<Long> tagIds) {
        List<QuestionListDto> questionList = questionService.searchQuestionByKeyword(keyword, tagIds);
        return ResponseDto.builder().status(HttpStatus.OK).message("조회 완료").data(questionList)
                .build();
    }

    @PostMapping
    public ResponseDto createQuestion(@RequestHeader("Authorization") String jwtToken,
            @RequestBody CreateQuestionDto question) {
        Long userId = jwtTokenProvider.getAuthUserId(jwtToken);
        if (!userId.equals(question.getQuestionWriterId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long questionId = questionService.createQuestion(question);
        ResponseQuestionDto responseQuestionDto = questionService.questionDetail(questionId);

        return ResponseDto.builder().status(HttpStatus.OK).data(responseQuestionDto).message("질문 등록 성공").build();
    }

    @GetMapping("/{questionId}")
    public ResponseDto questionDetail(@PathVariable Long questionId) {
        ResponseQuestionDto question = questionService.questionDetail(questionId);
        return ResponseDto.builder().status(HttpStatus.OK).message((questionId + "조회"))
                .data(question).build();
    }

    @PatchMapping
    public ResponseDto updateQuestion(@RequestHeader("Authorization") String jwtToken, @RequestBody UpdateQuestionDto updateQuestion){
        Long userId = jwtTokenProvider.getAuthUserId(jwtToken);
        log.info(userId + " " + updateQuestion.getUserId());
        if (!userId.equals(updateQuestion.getUserId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long questionId = questionService.updateQuestion(updateQuestion);
        ResponseQuestionDto responseQuestionDto = questionService.questionDetail(questionId);
        return ResponseDto.builder().status(HttpStatus.OK).data(responseQuestionDto).build();
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseDto deleteQuestion(@RequestHeader("Authorization") String jwtToken, @PathVariable Long questionId) {
        Long userId = jwtTokenProvider.getAuthUserId(jwtToken);
        if (!userId.equals(questionId)) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        questionService.deleteQuestion(questionId);
        return ResponseDto.builder().status(HttpStatus.OK).build();
    }

}
