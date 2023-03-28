package com.example.common.email.service.impl;

import com.example.common.email.service.EmailService;
import org.apache.commons.validator.GenericValidator;

public class EmailServiceImpl implements EmailService {
    @Override
    public Boolean sendEmail(String emailAddress) {
        if(!GenericValidator.isEmail(emailAddress)) return false;
        return null;
    }
}
