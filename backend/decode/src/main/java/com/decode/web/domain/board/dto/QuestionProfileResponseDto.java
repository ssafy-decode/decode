package com.decode.web.domain.board.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class QuestionProfileResponseDto {

    private Integer size;
    private List<QuestionProfileDto> questions;

}
