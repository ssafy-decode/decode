package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.dto.ResponseQuestionListDto;
import com.decode.web.domain.board.service.BookmarkService;
import com.decode.web.global.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.security.auth.login.CredentialException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @PostMapping
    @Operation(summary = "북마크 생성", description = "유저와 Question 간의 북마크 생성")
    public ResponseDto bookMark(@RequestBody BookmarkDto bookmarkDto, Authentication auth) {
        log.info("bookmarkDto: {}", bookmarkDto.toString());
        Long userId = (Long) auth.getPrincipal();

        if (!userId.equals(bookmarkDto.getUserId())) {
            throw new CredentialException("사용자 불일치");
        }
        Long bookMarkId = bookmarkService.bookMark(bookmarkDto);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message(bookMarkId + "북마크 완료")
                .data("")
                .build();
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "북마크 삭제", description = "해당 질문에 대한 유저 북마크 삭제")
    public ResponseDto unBookMark(@PathVariable Long questionId, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        bookmarkService.unBookMark(userId, questionId);
        return ResponseDto.
                builder()
                .status(HttpStatus.OK)
                .data("")
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "북마크 질문 리스트 조회", description = "유저가 북마크한 질문 조회")
    public ResponseDto getBookMarkQuestionList(@PathVariable Long userId) {
        List<ResponseQuestionListDto> bookMarkQuestionList = bookmarkService.getBookMarkQuestionList(
                userId);
        return ResponseDto.builder()
                .message("북마크 질문 리스트 조회 완료")
                .data(bookMarkQuestionList)
                .status(HttpStatus.OK)
                .build();
    }


}
