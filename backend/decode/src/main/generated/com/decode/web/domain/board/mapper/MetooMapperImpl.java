package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.MetooDto;
import com.decode.web.entity.MetooEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-23T10:08:09+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class MetooMapperImpl implements MetooMapper {

    @Override
    public MetooDto toDto(MetooEntity entity) {
        if (entity == null) {
            return null;
        }

        MetooDto metooDto = new MetooDto();

        return metooDto;
    }

    @Override
    public MetooEntity toEntity(MetooDto dto) {
        if (dto == null) {
            return null;
        }

        MetooEntity.MetooEntityBuilder metooEntity = MetooEntity.builder();

        return metooEntity.build();
    }
}
