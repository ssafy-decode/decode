package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.service.BookmarkService;
import com.decode.web.global.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseDto bookMark(@RequestBody Long userId, @RequestBody Long questionId){
        Long bookMarkId = bookmarkService.bookMark(userId, questionId);

        return ResponseDto.builder().status(HttpStatus.OK).message(bookMarkId+"북마크 완료").build();
    }

    @DeleteMapping("/{userId}/{questionId}")
    public ResponseDto unBookMark(@PathVariable Long userId, @PathVariable Long questionId){
        bookmarkService.unBookMark(userId, questionId);
        return ResponseDto.builder().status(HttpStatus.OK).build();
    }


}
