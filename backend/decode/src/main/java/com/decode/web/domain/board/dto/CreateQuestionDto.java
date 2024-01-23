package com.decode.web.domain.board.dto;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private Long questionWriterId;
    private List<QuestionTagDto> tags;
}
