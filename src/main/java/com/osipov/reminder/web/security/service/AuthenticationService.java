package com.osipov.reminder.web.security.service;

import com.osipov.reminder.domain.dto.auth.ForgotPasswordRequest;
import com.osipov.reminder.domain.dto.auth.JwtAuthResponse;
import com.osipov.reminder.domain.dto.auth.ResetPasswordRequest;
import com.osipov.reminder.domain.dto.auth.SignInRequest;
import com.osipov.reminder.domain.dto.user.UserCreateDto;

public interface AuthenticationService {

    JwtAuthResponse signUp(UserCreateDto dto);

    JwtAuthResponse signIn(SignInRequest request);

    void sendResetPasswordEmail(ForgotPasswordRequest request);

    void updatePassword(String token, ResetPasswordRequest request);

}
