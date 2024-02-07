package com.decode.web.domain.board.controller;

import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.domain.board.dto.UpdateCommentDto;
import com.decode.web.domain.board.service.CommentService;
import com.decode.web.entity.CommentEntity;
import com.decode.web.global.ResponseDto;
import javax.security.auth.login.CredentialException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;


    @PostMapping()
    public ResponseDto createComment(@RequestBody CreateCommentDto createCommentDto,
            Authentication authentication) throws CredentialException {
        log.debug("createCommentDto : {}", createCommentDto);
        validateUser((Long) authentication.getPrincipal(), createCommentDto.getUserId());
        Long commentId = commentService.save(createCommentDto);
        log.debug("created commentId : {}", commentId);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("댓글 생성 성공")
                .data(commentId)
                .build();
    }


    @DeleteMapping("/{commentId}")
    public ResponseDto deleteComment(@PathVariable Long commentId, Authentication authentication)
            throws BadRequestException {
        commentService.delete((Long) authentication.getPrincipal(), commentId);
        return ResponseDto.
                builder()
                .status(HttpStatus.OK)
                .message("댓글 삭제 성공")
                .data("")
                .build();
    }

    @PatchMapping()
    public ResponseDto updateComment(UpdateCommentDto updateCommentDto,
            Authentication authentication) throws CredentialException {
        validateUser((Long) authentication.getPrincipal(), updateCommentDto.getId());
        CommentEntity commentEntity = commentService.update(updateCommentDto);
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("댓글 업데이트 성공")
                .data(commentEntity)
                .build();
    }

    private void validateUser(Long userId, Long requestUserId) throws CredentialException {
        if (!userId.equals(requestUserId)) {
            throw new CredentialException("사용자 불일치");
        }
    }

}
