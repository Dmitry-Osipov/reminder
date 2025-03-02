package com.osipov.reminder.mapper;

import com.osipov.reminder.data.entity.UserEntity;
import com.osipov.reminder.domain.config.MapperConfig;
import com.osipov.reminder.domain.dto.user.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void update(@MappingTarget UserEntity target, UserEntity source);

    UserResponseDto toDto(UserEntity user);

}
