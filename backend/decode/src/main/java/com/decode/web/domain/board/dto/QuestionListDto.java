package com.decode.web.domain.board.dto;

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
public class QuestionListDto {

    private Long id;
    private String title;
    private ResponseUserProfileDto writer;
    private List<Long> tagList;
    private LocalDateTime createdTime;
    private int answerCnt;
    private int meTooCnt;

}
