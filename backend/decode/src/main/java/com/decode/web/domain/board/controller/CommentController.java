package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.domain.board.service.CommentService;
import com.decode.web.domain.board.service.CommentServiceImpl;
import com.decode.web.entity.CommentEntity;
import com.decode.web.global.ResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentServiceImpl commentServiceImpl){
        this.commentService = commentServiceImpl;
    }
    @PostMapping()
    public ResponseDto createComment(@RequestBody CreateCommentDto createCommentDto) {
        Long commentId = commentService.save(createCommentDto);
        return ResponseDto.builder().status(HttpStatus.OK).message("댓글 생성 성공").data(commentId).build();
    }
    @GetMapping("/{answerId}")
    public ResponseDto findByAnswer(@PathVariable String answerId){
        List<CommentEntity> commentEntityList = commentService.findByAnswer(answerId);
        return ResponseDto.builder().status(HttpStatus.OK).data(commentEntityList).message("답변에 달린 댓글 불러오기 성공").build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseDto deleteComment(@PathVariable String commentId) {
        commentService.delete(commentId);

        return ResponseDto.builder().status(HttpStatus.OK).message("댓글 삭제 성공").build();
    }

    @PatchMapping()
    public ResponseDto updateComment(UpdateCommentDto updateCommentDto) {
        CommentEntity commentEntity = commentService.update(updateCommentDto);
        return ResponseDto.builder().status(HttpStatus.OK).message("댓글 업데이트 성공").data(commentEntity).build();
    }

}
