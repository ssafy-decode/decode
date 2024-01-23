package com.decode.web.domain.board.dto;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import java.util.List;
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
public class UpdateQuestionDto {

    private Long userId;
    private Long questionId;
    private String title;
    private String content;
    private List<QuestionTagDto> tagList;
}
