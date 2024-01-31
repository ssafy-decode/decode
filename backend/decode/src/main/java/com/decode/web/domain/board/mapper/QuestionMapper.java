package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.entity.QuestionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDto toDto(QuestionEntity entity);

    QuestionEntity toEntity(QuestionDto dto);
}
