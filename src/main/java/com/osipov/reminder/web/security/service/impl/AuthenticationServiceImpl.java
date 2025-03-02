package com.osipov.reminder.web.security.service.impl;

import com.osipov.reminder.data.entity.UserEntity;
import com.osipov.reminder.domain.dto.auth.ForgotPasswordRequest;
import com.osipov.reminder.domain.dto.auth.JwtAuthResponse;
import com.osipov.reminder.domain.dto.auth.ResetPasswordRequest;
import com.osipov.reminder.domain.dto.auth.SignInRequest;
import com.osipov.reminder.domain.dto.user.UserCreateDto;
import com.osipov.reminder.domain.service.EmailService;
import com.osipov.reminder.domain.service.UserService;
import com.osipov.reminder.mapper.AuthMapper;
import com.osipov.reminder.mapper.UserMapper;
import com.osipov.reminder.web.security.service.AuthenticationService;
import com.osipov.reminder.web.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper mapper;
    private final UserMapper userMapper;

    @Override
    public JwtAuthResponse signUp(UserCreateDto dto) {
        UserEntity user = mapper.toEntity(dto, passwordEncoder.encode(dto.getPassword()));
        userService.create(user);
        return mapper.convert(jwtService.generateToken(user));
    }

    @Override
    public JwtAuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails currentUser = userService.userDetailsService().loadUserByUsername(request.getUsername());
        return mapper.convert(jwtService.generateToken(currentUser));
    }

    @Override
    public void sendResetPasswordEmail(ForgotPasswordRequest request) {
        UserEntity user = userService.getEntityByUsername(request.getUsername());
        mapper.fillResetPasswordData(user, UUID.randomUUID().toString());
        user = userService.update(user);
        emailService.sendResetPasswordEmail(request.getEmail(), user.getResetPasswordToken());
    }

    @Override
    public void updatePassword(String token, ResetPasswordRequest request) {
        UserEntity user = userService.getByResetPasswordToken(token);
        mapper.dropResetPasswordData(user, passwordEncoder.encode(request.getPassword()));
        userService.update(user);
    }

}
