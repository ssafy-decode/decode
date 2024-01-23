package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.QuestionDto;
import com.decode.web.entity.QuestionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-22T13:44:54+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public QuestionDto toDto(QuestionEntity entity) {
        if (entity == null) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        questionDto.setTitle(entity.getTitle());
        questionDto.setQuestionWriter(entity.getQuestionWriter());
        questionDto.setContent(entity.getContent());
        questionDto.setUpdatedTime(entity.getUpdatedTime());

        return questionDto;
    }

    @Override
    public QuestionEntity toEntity(QuestionDto dto) {
        if (dto == null) {
            return null;
        }

        QuestionEntity.QuestionEntityBuilder questionEntity = QuestionEntity.builder();

        questionEntity.title(dto.getTitle());
        questionEntity.questionWriter(dto.getQuestionWriter());
        questionEntity.content(dto.getContent());

        return questionEntity.build();
    }
}
