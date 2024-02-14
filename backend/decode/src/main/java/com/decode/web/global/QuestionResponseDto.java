package com.decode.web.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
public class QuestionResponseDto {

    private HttpStatus status;
    private String message;
    private Object data;
    private Object bookmarkList;
    private Object meTooList;

    public QuestionResponseDto() {

    }
}