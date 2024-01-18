package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.CommentDto;
import com.decode.web.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    public CommentDto toDto(CommentEntity entity);
    public CommentEntity toEntity(CommentDto dto);
}
