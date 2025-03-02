package com.osipov.reminder.web.controller;

import com.osipov.reminder.domain.dto.auth.ChangePasswordRequest;
import com.osipov.reminder.domain.dto.user.UserResponseDto;
import com.osipov.reminder.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер для работы с пользователями")
@RequestMapping(value = "${spring.data.rest.base-path}/users", produces = "application/json")
public class UserController {
    private final UserService service;

    @GetMapping("/{username}")
    @Operation(summary = "Получить пользователя по его имени пользователя (логину)")
    public UserResponseDto getByUsername(@PathVariable @NotBlank @Size(min = 5, max = 50) String username) {
        return service.getByUsername(username);
    }

    @PutMapping("/password")
    @Operation(summary = "Метод обновления пароля у пользователя")
    @PreAuthorize("#request.username == authentication.principal.username")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid ChangePasswordRequest request) {
        service.updatePassword(request);
    }

}
