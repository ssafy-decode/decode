package com.decode.web.domain.tag.mapper;

import com.decode.web.domain.tag.dto.TagDto;
import com.decode.web.entity.TagEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-22T13:44:54+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto toDto(TagEntity entity) {
        if (entity == null) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setTagName(entity.getTagName());

        return tagDto;
    }

    @Override
    public TagEntity toEntity(TagDto dto) {
        if (dto == null) {
            return null;
        }

        TagEntity.TagEntityBuilder tagEntity = TagEntity.builder();

        tagEntity.tagName(dto.getTagName());

        return tagEntity.build();
    }
}
