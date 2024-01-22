package com.decode.web.domain.board.dto;

import com.decode.web.domain.user.dto.UserProfileDto;
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
    private UserProfileDto commentWriter;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
