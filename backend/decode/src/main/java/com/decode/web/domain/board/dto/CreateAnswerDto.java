package com.decode.web.domain.board.dto;

public class CreateAnswerDto {

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

    private Long questionId;
    private Long userId;
    private String content;

}
