package com.example.common.config;

import com.example.common.utils.PasswordEncoderUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderConfig {
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
    }
}
