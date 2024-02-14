package com.decode.web.domain.chat.dto;

import lombok.Data;

@Data
public class DeleteSubRoomRequestDto {

    private Long userId;
    private Long roomId;
}
