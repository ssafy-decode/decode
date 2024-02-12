package com.decode.web.domain.board.dto;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import java.time.LocalDateTime;
import java.util.List;
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
public class ResponseQuestionDto {

    private Long id;
    private String title;
    private String content;
    private ResponseUserProfileDto questionWriter;
    private List<QuestionTagDto> tagList;
    private List<ResponseAnswerDto> answerList;
    private int meTooCnt;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private int bookmarkCnt;
}
