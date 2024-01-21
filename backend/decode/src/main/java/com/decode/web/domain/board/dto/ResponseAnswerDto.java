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
public class ResponseAnswerDto {
    private Long answerId;
    private UserProfileDto answerWriter;
    private String content;
    private boolean isAdopted;
//    private Long recommendCnt;
    private List<ResponseCommentDto> commentList;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
