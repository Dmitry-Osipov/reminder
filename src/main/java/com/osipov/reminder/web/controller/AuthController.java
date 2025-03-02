package com.osipov.reminder.web.controller;

import com.osipov.reminder.domain.dto.auth.ForgotPasswordRequest;
import com.osipov.reminder.domain.dto.auth.JwtAuthResponse;
import com.osipov.reminder.domain.dto.auth.ResetPasswordRequest;
import com.osipov.reminder.domain.dto.auth.SignInRequest;
import com.osipov.reminder.domain.dto.user.UserCreateDto;
import com.osipov.reminder.web.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер для работы с аутентификацией")
@RequestMapping(value = "${spring.data.rest.base-path}/auth", produces = "application/json")
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(summary = "Регистрация с получением JWT-токена")
    public JwtAuthResponse signUp(@RequestBody @Valid UserCreateDto dto) {
        return service.signUp(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация с получением JWT-токена")
    public JwtAuthResponse signIn(@RequestBody @Valid SignInRequest request) {
        return service.signIn(request);
    }

    @PostMapping("/password/forgot")
    @Operation(summary = "Метод для отправки пользователю ссылку на восстановление пароля")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        service.sendResetPasswordEmail(request);
    }

    @PostMapping("/password/reset")
    @Operation(summary = "Метод обновления пароля пользователю")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestParam(value = "token") String token,
                              @RequestBody @Valid ResetPasswordRequest request) {
        service.updatePassword(token, request);
    }

}
