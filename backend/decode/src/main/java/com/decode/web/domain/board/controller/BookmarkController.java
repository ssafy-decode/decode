package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.board.service.BookmarkService;
import com.decode.web.global.ResponseDto;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final JwtTokenProvider jwtTokenProvider;
    private final QuestionRepository questionRepository;


    @PostMapping("/")
    @Operation(summary = "북마크 생성", description = "유저와 Question 간의 북마크 생성")
    public ResponseDto bookMark(@RequestHeader("Authorization") String jwtToken,
            @RequestBody BookmarkDto bookmarkDto) {
        Long userId = jwtTokenProvider.getAuthUserId(jwtToken);
        if (!userId.equals(bookmarkDto.getUserId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        Long bookMarkId = bookmarkService.bookMark(bookmarkDto);
        return ResponseDto.builder().status(HttpStatus.OK).message(bookMarkId + "북마크 완료").build();
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "북마크 생성", description = "유저와 Question 간의 북마크 생성")
    public ResponseDto unBookMark(@RequestHeader("Authorization") String jwtToken,
            @PathVariable Long questionId) {
        Long userId = jwtTokenProvider.getAuthUserId(jwtToken);
        if (!userId.equals(
                questionRepository.getReferenceById(questionId).getQuestionWriter().getId())) {
            return ResponseDto.builder().status(HttpStatus.BAD_REQUEST).message("사용자 불일치").build();
        }
        bookmarkService.unBookMark(userId, questionId);
        return ResponseDto.builder().status(HttpStatus.OK).build();
    }


}
