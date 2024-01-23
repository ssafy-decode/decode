package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.entity.UserProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    public UserProfileEntity toEntity(UserProfileDto dto);

    public UserProfileDto toDto(UserProfileEntity entity);
}
