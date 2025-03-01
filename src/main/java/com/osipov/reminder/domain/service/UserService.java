package com.osipov.reminder.domain.service;

import com.osipov.reminder.domain.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserEntity create(UserEntity user);

    UserEntity update(UserEntity user);

    void deleteByUsername(String username);

    UserEntity getByUsername(String username);

    UserEntity updatePassword(String username, String oldPassword, String newPassword);

    UserDetailsService userDetailsService();

    UserEntity getByResetPasswordToken(String token);

}
