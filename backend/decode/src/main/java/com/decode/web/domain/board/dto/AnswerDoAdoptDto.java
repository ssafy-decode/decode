package com.decode.web.domain.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class AnswerDoAdoptDto {

    private Long userId;
    private Long answerId;
}
