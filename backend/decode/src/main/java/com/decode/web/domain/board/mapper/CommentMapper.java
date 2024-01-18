package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.CommentDto;
import com.decode.web.global.CommonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    public CommentDto toDto(CommonEntity entity);
    public CommonEntity toEntity(CommentDto dto);
}
