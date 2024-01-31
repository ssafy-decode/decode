package com.decode.web.domain.board.dto;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateQuestionDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @DecimalMin(value = "0", inclusive = true)
    private Long questionWriterId;
    private List<QuestionTagDto> tags;
}
