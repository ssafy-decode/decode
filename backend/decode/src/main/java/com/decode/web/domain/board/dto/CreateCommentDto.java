package com.decode.web.domain.board.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
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
public class CreateCommentDto {

    @NotEmpty
    private String content;
    @DecimalMin(value = "0", inclusive = true)
    private Long userId;
    @DecimalMin(value = "0", inclusive = true)
    private Long answerId;
}
