package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.CreateQuestionDto;
import com.decode.web.domain.board.dto.QuestionDocument;
import com.decode.web.domain.board.dto.ResponseQuestionDto;
import com.decode.web.domain.board.dto.ResponseQuestionListDto;
import com.decode.web.domain.board.dto.UpdateQuestionDto;
import com.decode.web.domain.board.repository.QuestionELKRepository;
import com.decode.web.domain.board.service.BookmarkService;
import com.decode.web.domain.board.service.MetooService;
import com.decode.web.domain.board.service.QuestionService;
import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.service.PointService;
import com.decode.web.global.QuestionResponseDto;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.CredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionELKRepository questionELKRepository;
    private final PointService pointService;
    private final MetooService metooService;
    private final BookmarkService bookmarkService;

    @GetMapping
    @Operation(summary = "질문 검색(질문 목록 조회)", description = "keyword를 통한 질문 리스트 호출")
    public QuestionResponseDto questionSearch(@RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "tagIds") List<Long> tagIds, Authentication authentication) {
        List<Long> meTooList = new ArrayList<>();
        List<Long> bookmarkList = new ArrayList<>();
        if (authentication != null) {
            Long userId = (Long) authentication.getPrincipal();
            meTooList = metooService.getIds(userId);
            bookmarkList = bookmarkService.get(userId);
        }
        List<ResponseQuestionListDto> questionList = questionService.searchQuestionByKeyword(
                keyword, tagIds);
        return QuestionResponseDto.builder()
                .status(HttpStatus.OK)
                .message("조회 완료")
                .data(questionList)
                .bookmarkList(bookmarkList)
                .meTooList(meTooList)
                .build();
    }

    @PostMapping
    @Operation(summary = "질문 생성", description = "질문 생성")
    public ResponseDto createQuestion(@RequestBody CreateQuestionDto question,
            Authentication auth) throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        if (!userId.equals(question.getQuestionWriterId())) {
            throw new CredentialException("사용자 불일치");
        }
        Long questionId = questionService.createQuestion(question);
        ResponseQuestionDto responseQuestionDto = questionService.questionDetail(questionId);
        pointService.updateUserPointAndExp(userId, Point.QUESTION);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .data(responseQuestionDto)
                .message("질문 등록 성공")
                .build();
    }

    @GetMapping("/{questionId}")
    @Operation(summary = "질문 상세 조회", description = "질문리스트에서 해당 질문을 클릭")
    public ResponseDto questionDetail(@PathVariable Long questionId) {
        ResponseQuestionDto question = questionService.questionDetail(questionId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("질문번호 " + questionId + " 조회")
                .data(question)
                .build();
    }

    @PatchMapping
    @Operation(summary = "질문 수정", description = "작성자와 일치하는 사용자의 토큰을 식별 후 수정")
    public ResponseDto updateQuestion(@RequestBody UpdateQuestionDto updateQuestion,
            Authentication auth) throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        if (!userId.equals(updateQuestion.getUserId())) {
            throw new CredentialException("사용자 불일치");
        }
        ResponseQuestionDto responseQuestionDto = questionService.updateQuestion(updateQuestion);
        return ResponseDto.builder()
                .message("질문 수정 성공")
                .status(HttpStatus.OK)
                .data(responseQuestionDto)
                .build();
    }

    @DeleteMapping("/delete/{questionId}")
    @Operation(summary = "질문 삭제", description = "작성자와 일치하는 사용자의 토큰을 식별 후 삭제")
    public ResponseDto deleteQuestion(@PathVariable Long questionId, Authentication auth)
            throws CredentialException {
        Long userId = (Long) auth.getPrincipal();
        QuestionDocument questionDocument = questionELKRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Question not found with id: " + questionId));
        if (!userId.equals(questionDocument.getWriterId())) {
            throw new CredentialException("사용자 불일치");
        }
        questionService.deleteQuestion(questionDocument);
        return ResponseDto.builder()
                .message("질문 삭제 성공")
                .data("")
                .status(HttpStatus.OK)
                .build();
    }


    @GetMapping("/list/{userId}")
    public ResponseDto getQuestionListByUserId(@PathVariable Long userId) {
        BoardProfileResponseDto data = questionService.findAllByUserId(userId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("질문 목록 조회 완료")
                .data(data)
                .build();
    }
}
