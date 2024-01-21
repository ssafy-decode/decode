package com.decode.web.domain.board.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookmarkDto {
    private Long userId;
    private Long questionId;
}
