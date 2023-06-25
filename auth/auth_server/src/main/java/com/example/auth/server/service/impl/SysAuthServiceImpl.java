package com.example.auth.server.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.example.auth.server.service.SysAuthService;
import com.example.common.core.exception.CustomException;
import com.example.common.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.UUID;

import static com.example.common.core.constant.RedisKeyConstant.captcha.CAPTCHA_UUID;

@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
    private final RedisUtil redisUtil;

    @Override
    public String captcha() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(90, 34, 4, 3);
        captcha.setBackground(Color.WHITE);
        String key = CAPTCHA_UUID + UUID.randomUUID();
        if (redisUtil.isExists(key)) {
            throw new CustomException("Already have CAPTCHA_UUID.");
        }
        redisUtil.set(key, captcha.getCode(), 60);
        return captcha.getImageBase64Data();
    }
}
