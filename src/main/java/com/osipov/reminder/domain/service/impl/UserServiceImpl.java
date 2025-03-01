package com.osipov.reminder.domain.service.impl;

import com.osipov.reminder.domain.entity.UserEntity;
import com.osipov.reminder.domain.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    @Transactional
    public UserEntity create(UserEntity user) {
        return null;
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity user) {
        return null;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUsername(String username) {
        return null;
    }

    @Override
    @Transactional
    public UserEntity updatePassword(String username, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    @Transactional
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByResetPasswordToken(String token) {
        return null;
    }

}
