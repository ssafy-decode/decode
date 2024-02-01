package com.decode.web.domain.board.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateAnswerDto {

    @DecimalMin(value = "0", inclusive = true)
    private Long questionId;
    @DecimalMin(value = "0", inclusive = true)
    private Long userId;
    @NotBlank
    private String content;

}
