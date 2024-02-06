package com.decode.web.domain.tag.mapper;

import com.decode.web.domain.tag.dto.QuestionTagDto;
import com.decode.web.entity.QuestionTagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionTagMapper {

    QuestionTagEntity toEntity(QuestionTagDto questionTagDto);

    QuestionTagDto toDto(QuestionTagEntity entity);
}
