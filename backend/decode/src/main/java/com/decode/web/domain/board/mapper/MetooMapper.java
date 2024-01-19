package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.entity.MetooEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetooMapper {

    public MetooDto toDto(MetooEntity entity);

    public MetooEntity toEntity(MetooDto dto);
}
