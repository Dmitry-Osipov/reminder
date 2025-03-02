package com.osipov.reminder.domain.service.impl;

import com.osipov.reminder.data.entity.UserEntity;
import com.osipov.reminder.data.repository.UserRepository;
import com.osipov.reminder.domain.dto.auth.ChangePasswordRequest;
import com.osipov.reminder.domain.dto.user.UserResponseDto;
import com.osipov.reminder.domain.exceptions.EntityContainedException;
import com.osipov.reminder.domain.exceptions.NotFoundException;
import com.osipov.reminder.domain.service.UserService;
import com.osipov.reminder.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("java:S6809")
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserEntity create(UserEntity user) {
        checkUsernameAndEmail(user.getUsername(), user.getEmail());
        return repository.save(user);
    }

    @Override
    @Transactional
    public UserEntity update(UserEntity user) {
        UserEntity entity = repository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Not found user with id " + user.getId()));
        checkUsernameAndEmail(user.getUsername(), user.getEmail());
        mapper.update(entity, user);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getEntityByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Not found user with username " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getByUsername(String username) {
        return mapper.toDto(getEntityByUsername(username));
    }

    @Override
    @Transactional
    public void updatePassword(ChangePasswordRequest request) {
        UserEntity user = getEntityByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password " + request.getOldPassword());
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService() {
        return this::getEntityByUsername;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByResetPasswordToken(String token) {
        return repository.findByResetPasswordToken(token)
                .orElseThrow(() -> new NotFoundException("Not found user with token " + token));
    }

    private void checkUsernameAndEmail(String username, String email) {
        if (repository.existsByUsername(username)) {
            throw new EntityContainedException("Username already exists " + username);
        }

        if (repository.existsByEmail(email)) {
            throw new EntityContainedException("Email already exists " + email);
        }
    }
}
