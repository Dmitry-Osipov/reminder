package com.osipov.reminder.web.security.service;

import com.osipov.reminder.domain.entity.UserEntity;

public interface AuthenticationService {

    String signUp(UserEntity user);

    String signIn(UserEntity user);

    void sendResetPasswordEmail(String email, String username);

    UserEntity getByResetPasswordToken(String token);

    void updatePassword(String token, String newPassword);

}
