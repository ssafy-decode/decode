package com.decode.web.mapper;

import com.decode.web.dto.UserDto;
import com.decode.web.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public UserDto toDto(UserEntity entity);
  public UserEntity toEntity(UserDto dto);

}
