package com.decode.web.domain.tag.mapper;

import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    public TagDto toDto(TagEntity entity);
    public TagEntity toEntity(TagDto dto);
}
