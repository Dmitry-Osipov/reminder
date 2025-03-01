package com.osipov.reminder.web.security.service.impl;

import com.osipov.reminder.domain.entity.UserEntity;
import com.osipov.reminder.domain.enums.Role;
import com.osipov.reminder.domain.exceptions.ResetPasswordTokenException;
import com.osipov.reminder.web.security.service.AuthenticationService;
import com.osipov.reminder.domain.service.EmailService;
import com.osipov.reminder.domain.service.UserService;
import com.osipov.reminder.web.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public String signUp(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        userService.create(user);
        return jwtService.generateToken(user);
    }

    @Override
    public String signIn(UserEntity user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        UserDetails currentUser = userService.userDetailsService().loadUserByUsername(user.getUsername());
        return jwtService.generateToken(currentUser);
    }

    @Override
    public void sendResetPasswordEmail(String email, String username) {
        UserEntity user = createPasswordResetToken(username);
        emailService.sendResetPasswordEmail(email, user.getResetPasswordToken());
    }

    @Override
    public UserEntity getByResetPasswordToken(String token) {
        UserEntity user = userService.getByResetPasswordToken(token);
        return validateResetPasswordToken(user);
    }

    @Override
    public void updatePassword(String token, String newPassword) {
        UserEntity user = userService.getByResetPasswordToken(token);
        user.setResetPasswordToken(null);  // TODO: mapper
        user.setResetPasswordTokenExpiryDate(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.update(user);
    }

    private UserEntity createPasswordResetToken(String username) {
        UserEntity user = userService.getByUsername(username);
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);  // TODO: mapper
        user.setResetPasswordTokenExpiryDate(new Date(System.currentTimeMillis() + 3600 * 1000));
        return userService.update(user);
    }

    private static UserEntity validateResetPasswordToken(UserEntity user) {
        if (user.getResetPasswordTokenExpiryDate().before(new Date())) {
            throw new ResetPasswordTokenException("Token expired");
        }

        return user;
    }

}
