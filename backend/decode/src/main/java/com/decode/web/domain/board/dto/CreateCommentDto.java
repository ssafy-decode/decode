package com.decode.web.domain.board.dto;

import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.UserInfoEntity;
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

    private String content;
    private Long userId;
    private Long answerId;
}
