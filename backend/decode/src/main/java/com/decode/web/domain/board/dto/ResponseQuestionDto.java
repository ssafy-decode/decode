package com.decode.web.domain.board.dto;

import com.decode.web.domain.user.dto.UserProfileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseQuestionDto {
    private Long id;
    private String title;
    private String content;
    private UserProfileDto questionWriter;
    private List<ResponseAnswerDto> answerList;
    private Long meTooCnt;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
