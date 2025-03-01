package com.osipov.reminder.domain.service.impl;

import com.osipov.reminder.domain.exceptions.EmailException;
import com.osipov.reminder.domain.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendResetPasswordEmail(String email, String token) {
        String subject = "Reset Password";
        String content = "To reset your password, click the link below:\n" +
                "http://localhost:8080/api/auth/password/reset?token=" + token;  // TODO: hardcode

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            throw new EmailException(e);
        }
    }
}
