package com.osipov.reminder.domain.service;

import com.osipov.reminder.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {  // TODO: implements - before should write controller

    User create(User user);

    User update(User user);

    void deleteByUsername(String username);

    User getByUsername(String username);

    User updatePassword(String username, String oldPassword, String newPassword);

    UserDetailsService userDetailsService();

    User getByResetPasswordToken(String token);

}
