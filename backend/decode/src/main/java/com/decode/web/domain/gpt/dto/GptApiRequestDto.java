package com.decode.web.domain.gpt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GptApiRequestDto {

    @NotNull
    @NotBlank
    private String content;
}
