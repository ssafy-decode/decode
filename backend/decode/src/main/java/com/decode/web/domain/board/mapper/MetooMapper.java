package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.entity.MetooEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetooMapper {

    MetooDto toDto(MetooEntity entity);

    MetooEntity toEntity(MetooDto dto);
}
