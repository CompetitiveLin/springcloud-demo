package com.example.activity.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.example.activity.service.EmailService;
import com.example.common.core.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;

    @Override
    public Boolean sendEmail(String emailAddress) {
        if(!Validator.isEmail(emailAddress)){
            throw new CustomException("Not a valid email address.");
        }
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            String verificationCode = RandomUtil.randomNumbers(4);
            message.setFrom(username);
            message.setTo(emailAddress);
            message.setSubject("Verification Code");
            message.setText("Code: " + verificationCode);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new CustomException("Sending mail failed.");
        }
        return true;
    }
}
