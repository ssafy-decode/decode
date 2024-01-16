package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserDto;
import com.decode.web.domain.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public UserDto toDto(UserEntity entity);
  public UserEntity toEntity(UserDto dto);

}
