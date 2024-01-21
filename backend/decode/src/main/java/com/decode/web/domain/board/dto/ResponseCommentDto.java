package com.decode.web.domain.board.dto;

import com.decode.web.domain.user.dto.UserProfileDto;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseCommentDto {
    private Long commentId;
    private String content;
    private UserProfileDto commentWriter;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
