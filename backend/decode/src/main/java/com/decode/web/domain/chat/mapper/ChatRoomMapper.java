package com.decode.web.domain.chat.mapper;

import com.decode.web.domain.chat.dto.ChatRoomRequestDto;
import com.decode.web.entity.ChatRoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {

    ChatRoomRequestDto toDto(ChatRoomEntity entity);

    ChatRoomEntity toEntity(ChatRoomRequestDto dto);
}
