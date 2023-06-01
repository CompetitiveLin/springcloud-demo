package com.example.activity.service.impl;

import com.example.activity.service.UserService;
import com.example.common.core.constant.RedisKeyConstant;
import com.example.common.core.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public ResponseResult signIn() {
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern("yyyyMM:"));
        String key = RedisKeyConstant.user.USER_SIGN_IN + keySuffix + "Username:";
        int dayOfMonth = now.getDayOfMonth();
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth -1, true);
        return ResponseResult.success();
    }
}
