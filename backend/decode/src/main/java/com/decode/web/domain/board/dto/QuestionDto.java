package com.decode.web.domain.board.dto;

import java.time.LocalDateTime;
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
public class QuestionDto {

    private Long id;
    private String title;
    private Long question_writer_id;
    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updatedTime;

}
