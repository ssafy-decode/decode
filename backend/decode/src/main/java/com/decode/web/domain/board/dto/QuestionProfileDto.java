package com.decode.web.domain.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class QuestionProfileDto {

    private String title;
    private Long questionId;
}
