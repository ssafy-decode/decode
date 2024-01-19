package com.decode.web.domain.board.dto;

import com.decode.web.entity.UserInfoEntity;
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

    private String title;
    private UserInfoEntity questionWriter;
    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updatedTime;

    public QuestionDto(InputQuestionDto question) {
        this.title = question.getTitle();
        this.content = question.getContent();
        this.createTime = question.getCreateTime();
        this.updatedTime = question.getUpdateTime();
    }
}
