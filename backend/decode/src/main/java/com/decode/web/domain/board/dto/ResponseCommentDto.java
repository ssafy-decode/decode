package com.decode.web.domain.board.dto;

import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import java.time.LocalDateTime;
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
public class ResponseCommentDto {

    private Long commentId;
    private String content;
    private ResponseUserProfileDto commentWriter;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
