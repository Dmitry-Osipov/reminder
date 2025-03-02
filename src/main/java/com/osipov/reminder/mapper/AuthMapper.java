package com.osipov.reminder.mapper;

import com.osipov.reminder.data.entity.UserEntity;
import com.osipov.reminder.domain.config.MapperConfig;
import com.osipov.reminder.domain.dto.auth.JwtAuthResponse;
import com.osipov.reminder.domain.dto.user.UserCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface AuthMapper {

    JwtAuthResponse convert(String token);

    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", expression = "java(com.osipov.reminder.data.enums.Role.ROLE_USER)")
    UserEntity toEntity(UserCreateDto dto, String password);

    @Mapping(target = "resetPasswordToken", expression = "java(null)")
    @Mapping(target = "resetPasswordTokenExpiryDate", expression = "java(null)")
    void dropResetPasswordData(@MappingTarget UserEntity user, String password);

    @Mapping(target = "resetPasswordToken", source = "token")
    @Mapping(target = "resetPasswordTokenExpiryDate",
            expression = "java(new java.util.Date(System.currentTimeMillis() + 3600 * 1000))")
    void fillResetPasswordData(@MappingTarget UserEntity user, String token);

}
