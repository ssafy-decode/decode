package com.decode.web.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class ChatRequestDto {

    private Long roomId; // 방번호
    private Long userId; // 메시지 보낸사람
    private String nickName;
    private String text; // 메시지

}
