package com.example.common.email.service.impl;

import cn.hutool.core.lang.Validator;
import com.example.common.core.exception.CustomException;
import com.example.common.email.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public Boolean sendEmail(String emailAddress) {
        if(!Validator.isEmail(emailAddress)){
            throw new CustomException("Not a valid email address.");
        }
        return null;
    }
}
