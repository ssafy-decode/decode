package com.decode.web.domain.User.mapper;

import com.decode.web.domain.User.dto.UserDto;
import com.decode.web.domain.User.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public UserDto toDto(UserEntity entity);
  public UserEntity toEntity(UserDto dto);

}
