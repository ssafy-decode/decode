package com.decode.web.domain.board.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookmarkDto {

    @DecimalMin(value = "0", inclusive = true)
    private Long userId;
    @DecimalMin(value = "0", inclusive = true)
    private Long questionId;
}
