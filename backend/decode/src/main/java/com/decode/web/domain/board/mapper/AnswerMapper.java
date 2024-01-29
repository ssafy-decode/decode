package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.AnswerDto;
import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.entity.AnswerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerDto toDto(AnswerEntity entity);

    AnswerEntity toEntity(AnswerDto dto);

    AnswerEntity toEntity(CreateAnswerDto createAnswerDto);
}
