package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.BookmarkDto;
import com.decode.web.entity.BookmarkEntity;
import org.mapstruct.Mapper;

import java.awt.image.ColorModel;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {
    public BookmarkDto toDto(BookmarkEntity entity);
    public BookmarkEntity toEntity(BookmarkDto dto);
}
