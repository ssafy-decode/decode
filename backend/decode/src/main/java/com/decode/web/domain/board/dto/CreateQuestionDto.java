package com.decode.web.domain.board.dto;

import java.time.LocalDateTime;
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

    private String title;
    private String content;
    private Long questionWriterId;
    private List<QuestionTagDto> tags;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
