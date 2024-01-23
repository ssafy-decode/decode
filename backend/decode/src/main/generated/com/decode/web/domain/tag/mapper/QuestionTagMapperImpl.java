package com.decode.web.domain.tag.mapper;

import com.decode.web.domain.board.dto.QuestionTagDto;
import com.decode.web.entity.QuestionTagEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-23T10:08:09+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class QuestionTagMapperImpl implements QuestionTagMapper {

    @Override
    public QuestionTagEntity toEntity(QuestionTagDto questionTagDto) {
        if ( questionTagDto == null ) {
            return null;
        }

        QuestionTagEntity.QuestionTagEntityBuilder questionTagEntity = QuestionTagEntity.builder();

        questionTagEntity.version( questionTagDto.getVersion() );

        return questionTagEntity.build();
    }

    @Override
    public QuestionTagDto toDto(QuestionTagEntity entity) {
        if ( entity == null ) {
            return null;
        }

        QuestionTagDto questionTagDto = new QuestionTagDto();

        questionTagDto.setVersion( entity.getVersion() );

        return questionTagDto;
    }
}
