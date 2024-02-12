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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseQuestionListDto {

    private Long id;
    private String title;
    private ResponseUserProfileDto writer;
    private List<QuestionTagDto> tagList;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private int answerCnt;
    private int meTooCnt;
    private int bookmarkCnt;

}
