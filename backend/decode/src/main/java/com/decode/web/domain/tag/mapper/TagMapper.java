package com.decode.web.domain.tag.mapper;

import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(TagEntity entity);

    TagEntity toEntity(TagDto dto);
}
