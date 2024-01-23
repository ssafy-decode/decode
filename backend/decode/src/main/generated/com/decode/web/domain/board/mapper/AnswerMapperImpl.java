package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.AnswerDto;
import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.entity.AnswerEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-22T13:44:54+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public AnswerDto toDto(AnswerEntity entity) {
        if (entity == null) {
            return null;
        }

        AnswerDto answerDto = new AnswerDto();

        return answerDto;
    }

    @Override
    public AnswerEntity toEntity(AnswerDto dto) {
        if (dto == null) {
            return null;
        }

        AnswerEntity answerEntity = new AnswerEntity();

        return answerEntity;
    }

    @Override
    public AnswerEntity toEntity(CreateAnswerDto createAnswerDto) {
        if (createAnswerDto == null) {
            return null;
        }

        AnswerEntity answerEntity = new AnswerEntity();

        answerEntity.setContent(createAnswerDto.getContent());

        return answerEntity;
    }
}
