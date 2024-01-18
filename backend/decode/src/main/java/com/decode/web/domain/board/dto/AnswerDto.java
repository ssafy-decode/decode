package com.decode.web.domain.board.dto;

import java.time.LocalDateTime;

public class AnswerDto {

    private Long answerId;
    private Long answerWriterId;
    private Long questionId;
    private boolean isAdopted;
    private String content;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
