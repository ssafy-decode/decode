package com.decode.web.domain.board.dto;

import java.time.LocalDateTime;

public class CommentDto {

    private Long commentId;
    private Long answerId;
    private Long commentWriterId;
    private String content;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
