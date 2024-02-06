package com.decode.web.domain.chat.mapper;

import com.decode.web.domain.chat.dto.ChatRequestDto;
import com.decode.web.entity.ChatEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatEntity toEntity(ChatRequestDto chatRequestDto);

    ChatRequestDto toDto(ChatEntity chatRequestDto);

}
