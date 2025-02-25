package com.osipov.reminder.domain.service.impl;

import com.osipov.reminder.domain.entity.User;
import com.osipov.reminder.domain.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {  // TODO: write

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deleteByUsername(String username) {
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User updatePassword(String username, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return null;
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return null;
    }

}
