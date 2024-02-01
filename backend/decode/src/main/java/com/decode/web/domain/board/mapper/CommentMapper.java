package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.CommentDto;
import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.entity.CommentEntity;
import com.decode.web.global.CommonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(CommonEntity entity);

    CommentEntity toEntity(CommentDto dto);

    CommentEntity toEntity(CreateCommentDto dto);

}
