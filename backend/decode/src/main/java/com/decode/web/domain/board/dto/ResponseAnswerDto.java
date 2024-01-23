package com.decode.web.domain.board.dto;

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
public class ResponseAnswerDto {

    private Long answerId;
    private ResponseUserProfileDto answerWriter;
    private String content;
    private boolean isAdopted;
    //    private Long recommendCnt;
    private List<ResponseCommentDto> commentList;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
