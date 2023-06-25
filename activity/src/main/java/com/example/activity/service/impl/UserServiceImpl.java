package com.example.activity.service.impl;

import com.example.activity.service.UserService;
import com.example.common.core.constant.RedisKeyConstant;
import com.example.common.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RList;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisUtil redisUtil;
    @Override
    public void checkOut() {
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern("yyyyMM:"));
        String key = RedisKeyConstant.user.USER_CHECKOUT + keySuffix + "USERNAME";
        int dayOfMonth = now.getDayOfMonth();
        redisTemplate.opsForValue().setBit(key, dayOfMonth -1, true);
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
        RList<String> rList = redisUtil.getList(RedisKeyConstant.user.USER_CHECKOUT_RANK);
        rList.add("USERNAME-" + time);
    }

    @Override
    public int checkoutContinuousCount() {
//        Long userId = UserHolder.getUser().getId();

        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern("yyyyMM:"));
        String key = RedisKeyConstant.user.USER_CHECKOUT + keySuffix + "USERNAME";
        int dayOfMonth = now.getDayOfMonth();
        List<Long> result = redisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        if (result == null || result.isEmpty() || result.get(0) == null || result.get(0) == 0) {
            return 0;
        }
        Long num = result.get(0);
        int count = 0;
        while ((num & 1) != 0) {
            count++;
            num >>>= 1;
        }
        return count;
    }

    @Override
    public int checkoutCount(String date) {
//        String userId = "1234";

        LocalDateTime dateOfSign = StringParseLocalDateTime(date);
        String keySuffix = dateOfSign.format(DateTimeFormatter.ofPattern("yyyyMM:"));
        String key = RedisKeyConstant.user.USER_CHECKOUT + keySuffix + "USERNAME";
        Long count = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
        assert count != null;
        return count.intValue();
    }

    @Override
    public void lateCheckout(String date) {
        LocalDateTime dateOfSign = StringParseLocalDateTime(date);
        String keySuffix = dateOfSign.format(DateTimeFormatter.ofPattern("yyyyMM:"));
        String key = RedisKeyConstant.user.USER_CHECKOUT + keySuffix + "USERNAME";
        int dayOfMonth = dateOfSign.getDayOfMonth();
        redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
    }

    /**
     * 字符串日期转换成LocalDateTime
     *
     * @param date    202301  202302
     * @return
     * @throws RuntimeException
     */
    private LocalDateTime StringParseLocalDateTime(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date parse = simpleDateFormat.parse(date);
            Instant instant = parse.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            return instant.atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("日期格式转换报错");
        }
    }
}
