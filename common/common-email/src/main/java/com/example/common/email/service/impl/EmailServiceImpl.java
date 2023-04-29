package com.example.common.email.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.example.common.email.service.EmailService;
import com.example.common.core.exception.CustomException;
import com.example.common.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.example.common.core.constant.RedisKeyConstant.captcha.CAPTCHA_EMAIL_ADDRESS;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;

    private final RedisUtil redisUtil;

    @Override
    public void sendEmail(String emailAddress) {
        if(!Validator.isEmail(emailAddress)){
            throw new CustomException("Not a valid email address.");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        String verificationCode = RandomUtil.randomNumbers(4);
        message.setFrom(username);
        message.setTo(emailAddress);
        message.setSubject("Verification Code");
        message.setText("Code: " + verificationCode);
        String key = CAPTCHA_EMAIL_ADDRESS + emailAddress;
        if (redisUtil.hasKey(key)) {
            throw new CustomException("Already have CAPTCHA_EMAIL_ADDRESS.");
        }
        try{
            javaMailSender.send(message);
            log.info("Email send to {} successful, code is {}.", username, verificationCode);
        } catch (Exception e){
            throw new CustomException("Sending mail failed.");
        }
        redisUtil.set(key, verificationCode, 60);
    }
}
