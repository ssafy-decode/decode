package com.decode.web.domain.board.mapper;

import com.decode.web.domain.board.dto.CommentDto;
import com.decode.web.domain.board.dto.CreateCommentDto;
import com.decode.web.entity.CommentEntity;
import com.decode.web.global.CommonEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-23T10:08:09+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto toDto(CommonEntity entity) {
        if (entity == null) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        return commentDto;
    }

    @Override
    public CommentEntity toEntity(CommentDto dto) {
        if (dto == null) {
            return null;
        }

        CommentEntity.CommentEntityBuilder commentEntity = CommentEntity.builder();

        return commentEntity.build();
    }

    @Override
    public CommentEntity toEntity(CreateCommentDto dto) {
        if (dto == null) {
            return null;
        }

        CommentEntity.CommentEntityBuilder commentEntity = CommentEntity.builder();

        commentEntity.content(dto.getContent());

        return commentEntity.build();
    }
}
