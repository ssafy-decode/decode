package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.dto.QuestionListDto;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.board.service.BookmarkService;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final QuestionRepository questionRepository;

    @PostMapping
    @Operation(summary = "북마크 생성", description = "유저와 Question 간의 북마크 생성")
    public ResponseDto bookMark(@RequestBody BookmarkDto bookmarkDto, Authentication auth) {
        log.info("bookmarkDto: {}", bookmarkDto.toString());
        Long userId = (Long) auth.getPrincipal();

        if (!userId.equals(bookmarkDto.getUserId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long bookMarkId = bookmarkService.bookMark(bookmarkDto);
        return ResponseDto.builder().status(HttpStatus.OK).message(bookMarkId + "북마크 완료").build();
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "북마크 생성", description = "유저와 Question 간의 북마크 생성")
    public ResponseDto unBookMark(@PathVariable Long questionId, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        if (!userId.equals(
                questionRepository.getReferenceById(questionId).getQuestionWriter().getId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        bookmarkService.unBookMark(userId, questionId);
        return ResponseDto.builder().status(HttpStatus.OK).build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "북마크 질문 리스트 조회", description = "유저가 북마크한 질문 조회")
    public ResponseDto getBookMarkQuestionList(@PathVariable Long userId) {
        List<QuestionListDto> bookMarkQuestionList = bookmarkService.getBookMarkQuetionList(userId);
        return ResponseDto.builder().data(bookMarkQuestionList).status(HttpStatus.OK).build();
    }


}
