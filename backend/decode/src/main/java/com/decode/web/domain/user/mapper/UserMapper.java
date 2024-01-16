package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.entity.UserInfoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  public UserInfoDto toDto(UserInfoEntity entity);
  public UserInfoEntity toEntity(UserInfoDto dto);

}
