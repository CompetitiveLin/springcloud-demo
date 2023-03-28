package com.example.common.email.service.impl;

import cn.hutool.core.lang.Validator;
import com.example.common.email.service.EmailService;

public class EmailServiceImpl implements EmailService {
    @Override
    public Boolean sendEmail(String emailAddress) {
        if(!Validator.isEmail(emailAddress)) return false;
        return null;
    }
}
