package com.decode.web.domain.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetooQuestionDto {

    private Long id; // questionId
    private String title;
    private int answerCnt;
    private int meTooCnt;
    private int bookmarkCnt;
}
