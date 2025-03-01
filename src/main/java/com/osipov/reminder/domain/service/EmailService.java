package com.osipov.reminder.domain.service;

public interface EmailService {

    void sendResetPasswordEmail(String email, String token);

}
