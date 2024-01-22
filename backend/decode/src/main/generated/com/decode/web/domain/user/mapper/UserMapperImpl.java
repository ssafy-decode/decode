package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserInfoDto;
import com.decode.web.entity.UserInfoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-19T11:23:48+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserInfoDto toDto(UserInfoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserInfoDto userInfoDto = new UserInfoDto();

        userInfoDto.setId( entity.getId() );
        userInfoDto.setEmail( entity.getEmail() );
        userInfoDto.setNickname( entity.getNickname() );
        userInfoDto.setPassword( entity.getPassword() );
        userInfoDto.setPhoneNumber( entity.getPhoneNumber() );
        userInfoDto.setCreatedTime( entity.getCreatedTime() );
        userInfoDto.setUpdatedTime( entity.getUpdatedTime() );

        return userInfoDto;
    }

    @Override
    public UserInfoEntity toEntity(UserInfoDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserInfoEntity.UserInfoEntityBuilder userInfoEntity = UserInfoEntity.builder();

        userInfoEntity.id( dto.getId() );
        userInfoEntity.email( dto.getEmail() );
        userInfoEntity.nickname( dto.getNickname() );
        userInfoEntity.password( dto.getPassword() );
        userInfoEntity.phoneNumber( dto.getPhoneNumber() );

        return userInfoEntity.build();
    }
}
