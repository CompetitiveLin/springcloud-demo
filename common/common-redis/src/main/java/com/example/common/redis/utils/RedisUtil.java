package com.example.common.redis.utils;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
/**
 * @description: Redis工具类
 */
@RequiredArgsConstructor
public final class RedisUtil {

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

    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    public RLock getFairLock(String key) {
        return redissonClient.getFairLock(key);
    }

    public RLock getReadLock(String key) {
        return redissonClient.getReadWriteLock(key).readLock();
    }

    public RLock getWriteLock(String key) {
        return redissonClient.getReadWriteLock(key).writeLock();
    }

    public boolean tryLock(RLock lock, long expire, long timeout) throws InterruptedException {
        return lock.tryLock(timeout, expire, TimeUnit.MILLISECONDS);
    }

    public boolean tryLock(String key, long expire, long timeout) throws InterruptedException {
        return tryLock(getLock(key),expire,timeout);
    }

    public void unlock(String key) {
        unlock(getLock(key));
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }

    public void lock(String key) {
        lock(getLock(key));
    }

    public void lock(RLock lock) {
        lock.lock();
    }

    public boolean isLocked(String key) {
        return isLocked(getLock(key));
    }

    public boolean isLocked(RLock lock) {
        return lock.isLocked();
    }

    public boolean isHeldByCurrentThread(String key) {
        return isHeldByCurrentThread(getLock(key));
    }

    public boolean isHeldByCurrentThread(RLock lock) {
        return lock.isHeldByCurrentThread();
    }

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
        redissonClient.getBucket(key).setIfAbsent(value,Duration.ofSeconds(expire));
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

    public long incrementAndGet(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.expireIfNotSet(Duration.ofSeconds(HOUR_ONE_EXPIRE));
        return atomicLong.incrementAndGet();
    }

    public long decrementAndGet(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.expireIfNotSet(Duration.ofSeconds(HOUR_ONE_EXPIRE));
        return atomicLong.decrementAndGet();
    }

    public long addAndGet(String key,long value) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        long newValue = atomicLong.addAndGet(value);
        atomicLong.expireIfNotSet(Duration.ofSeconds(HOUR_ONE_EXPIRE));
        return newValue;
    }

    public long getAtomicValue(String key) {
        return redissonClient.getAtomicLong(key).get();
    }

    public void hSet(String key,String field, Object value,long expire) {
        RMap<Object, Object> map = redissonClient.getMap(key);
        map.put(field,value);
        map.expire(Duration.ofSeconds(expire));
    }

    public void hSet(String key,String field, Object value) {
        hSet(key, field, value,NOT_EXPIRE);
    }

    public Object hGet(String key,String field) {
        return redissonClient.getMap(key).get(field);
    }

}