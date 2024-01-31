package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.entity.BookmarkEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    BookmarkDto toDto(BookmarkEntity entity);

    BookmarkEntity toEntity(BookmarkDto dto);
}
