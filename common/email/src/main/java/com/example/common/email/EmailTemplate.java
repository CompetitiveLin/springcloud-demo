package com.example.common.email;

import com.example.common.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class EmailTemplate {
    private final EmailService emailService;

    @SneakyThrows
    public boolean send(String emailAddress){
        return emailService.sendEmail(emailAddress);
    }
}
