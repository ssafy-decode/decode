package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.AnswerDto;
import com.decode.web.entity.AnswerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    public AnswerDto toDto(AnswerEntity entity);

    public AnswerEntity toEntity(AnswerDto dto);
}
