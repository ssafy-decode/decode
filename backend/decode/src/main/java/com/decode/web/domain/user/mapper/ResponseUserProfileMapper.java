package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.entity.UserProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseUserProfileMapper {

    UserProfileEntity toEntity(ResponseUserProfileDto dto);

    ResponseUserProfileDto toDto(UserProfileEntity entity);

}
