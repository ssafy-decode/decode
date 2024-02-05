package com.decode.web.domain.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDoAdoptDto {

    private Long userId;
    private Long answerId;
}
