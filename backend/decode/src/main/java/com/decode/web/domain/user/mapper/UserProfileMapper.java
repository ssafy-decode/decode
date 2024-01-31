package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.entity.UserProfileEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileEntity toEntity(UserProfileDto dto);

    UserProfileDto toDto(UserProfileEntity entity);

    List<UserProfileDto> toDto(List<UserProfileEntity> entityList);
}
