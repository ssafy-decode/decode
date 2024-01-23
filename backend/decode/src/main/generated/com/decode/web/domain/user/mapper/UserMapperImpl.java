package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.entity.UserInfoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-23T10:08:09+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserInfoDto toDto(UserInfoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserInfoDto.UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        userInfoDto.id( entity.getId() );
        userInfoDto.email( entity.getEmail() );
        userInfoDto.password( entity.getPassword() );
        userInfoDto.phoneNumber( entity.getPhoneNumber() );
        userInfoDto.birth( entity.getBirth() );
        userInfoDto.name( entity.getName() );
        userInfoDto.createdTime( entity.getCreatedTime() );
        userInfoDto.updatedTime( entity.getUpdatedTime() );

        return userInfoDto.build();
    }

    @Override
    public UserInfoEntity toEntity(UserInfoDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserInfoEntity.UserInfoEntityBuilder userInfoEntity = UserInfoEntity.builder();

        userInfoEntity.id( dto.getId() );
        userInfoEntity.email( dto.getEmail() );
        userInfoEntity.password( dto.getPassword() );
        userInfoEntity.phoneNumber( dto.getPhoneNumber() );
        userInfoEntity.birth( dto.getBirth() );
        userInfoEntity.name( dto.getName() );

        return userInfoEntity.build();
    }
}
