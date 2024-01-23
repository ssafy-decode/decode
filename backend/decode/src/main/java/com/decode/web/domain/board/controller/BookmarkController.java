package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.domain.board.service.BookmarkService;
import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/")
    public ResponseDto bookMark(@RequestBody BookmarkDto bookmarkDto) {
        Long bookMarkId = bookmarkService.bookMark(bookmarkDto);

        return ResponseDto.builder().status(HttpStatus.OK).message(bookMarkId + "북마크 완료").build();
    }

    @DeleteMapping("/{questionId}")
    public ResponseDto unBookMark( @PathVariable Long questionId) {
        Long userId = 1L;
        bookmarkService.unBookMark(userId, questionId);
        return ResponseDto.builder().status(HttpStatus.OK).build();
    }


}
