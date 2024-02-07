package com.decode.web.domain.gpt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GptApiAnswerResponseDto {

    private String answer;

}
