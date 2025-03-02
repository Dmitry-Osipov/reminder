package com.osipov.reminder.domain.service;

import com.osipov.reminder.data.entity.UserEntity;
import com.osipov.reminder.domain.dto.auth.ChangePasswordRequest;
import com.osipov.reminder.domain.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserEntity create(UserEntity user);

    UserEntity update(UserEntity user);

    UserEntity getEntityByUsername(String username);

    UserResponseDto getByUsername(String username);

    void updatePassword(ChangePasswordRequest request);

    UserDetailsService userDetailsService();

    UserEntity getByResetPasswordToken(String token);

}
