package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.entity.BookmarkEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-23T10:08:09+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class BookmarkMapperImpl implements BookmarkMapper {

    @Override
    public BookmarkDto toDto(BookmarkEntity entity) {
        if (entity == null) {
            return null;
        }

        BookmarkDto bookmarkDto = new BookmarkDto();

        return bookmarkDto;
    }

    @Override
    public BookmarkEntity toEntity(BookmarkDto dto) {
        if (dto == null) {
            return null;
        }

        BookmarkEntity.BookmarkEntityBuilder bookmarkEntity = BookmarkEntity.builder();

        return bookmarkEntity.build();
    }
}
