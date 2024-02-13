package com.decode.web.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponseDto {

    private Long id;

    private Long roomId;

    private Long userId;

    private String nickName;

    private String text;

    private String createAt;
}
