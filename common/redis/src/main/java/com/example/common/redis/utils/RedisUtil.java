package com.example.common.redis.utils;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    private final RedissonClient redissonClient;

    /**  默认过期时长为24小时，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  过期时长为1小时，单位：秒 */
    public final static long HOUR_ONE_EXPIRE = 60 * 60;

    /**  过期时长为6小时，单位：秒 */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6;

    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1L;

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public void set(String key, Object value, long expire){
        redissonClient.getBucket(key).set(value,expire, TimeUnit.SECONDS);
    }

    public void setIfAbsent(String key, Object value) {
        setIfAbsent(key,value,DEFAULT_EXPIRE);
    }

    public void setIfAbsent(String key, Object value, long expire) {
        redissonClient.getBucket(key).setIfAbsent(value, Duration.ofSeconds(expire));
    }

    public Object get(String key) {
        return redissonClient.getBucket(key).get();
    }

    public void delete(String key) {
        redissonClient.getKeys().delete(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
