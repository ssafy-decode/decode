package com.decode.web.domain.user.mapper;

import com.decode.web.domain.user.dto.UserProfileDto;
import com.decode.web.entity.UserProfileEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-01-23T10:08:09+0900",
        comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfileEntity toEntity(UserProfileDto dto) {
        if (dto == null) {
            return null;
        }

        UserProfileEntity.UserProfileEntityBuilder userProfileEntity = UserProfileEntity.builder();

        userProfileEntity.nickname(dto.getNickname());
        userProfileEntity.exp(dto.getExp());
        userProfileEntity.tier(dto.getTier());
        userProfileEntity.profileImg(dto.getProfileImg());
        userProfileEntity.point(dto.getPoint());
        userProfileEntity.coin(dto.getCoin());

        return userProfileEntity.build();
    }

    @Override
    public UserProfileDto toDto(UserProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setExp(entity.getExp());
        userProfileDto.setPoint(entity.getPoint());
        userProfileDto.setCoin(entity.getCoin());
        userProfileDto.setNickname(entity.getNickname());
        userProfileDto.setTier(entity.getTier());
        userProfileDto.setProfileImg(entity.getProfileImg());

        return userProfileDto;
    }
}
