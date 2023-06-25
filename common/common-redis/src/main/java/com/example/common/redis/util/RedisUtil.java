package com.example.common.redis.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
/**
 * @description: Redis工具类
 */
@RequiredArgsConstructor
public final class RedisUtil {

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

//    public void lset(String key, String )


    /**
     * 用于操作key
     * @return RKeys 对象
     */
    public RKeys getKeys() {
        return redissonClient.getKeys();
    }
    /**
     * 移除缓存
     *
     * @param key
     */
    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    /**
     * 获取getBuckets 对象
     *
     * @return RBuckets 对象
     */
    public RBuckets getBuckets() {
        return redissonClient.getBuckets();
    }

    /**
     * 读取缓存中的字符串，永久有效
     *
     * @param key 缓存key
     * @return Object
     */
    public Object get(String key) {
        return redissonClient.getBucket(key).get();
    }


    /**
     * 缓存字符串
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 缓存带过期时间的字符串
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expire 缓存过期时间，long类型，必须传值
     */
    public void set(String key, Object value, long expire) {
        redissonClient.getBucket(key).set(value, expire <= 0L ? DEFAULT_EXPIRE : expire, TimeUnit.SECONDS);
    }

    /**
     * string 操作，如果不存在则写入缓存（string方式，不带有redisson的格式信息）
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param expire 缓存过期时间
     */
    public void setIfAbsent(String key, Object value, long expire) {
        redissonClient.getBucket(key).setIfAbsent(value,Duration.ofSeconds(expire));
    }

    /**
     * 如果不存在则写入缓存（string方式，不带有redisson的格式信息），默认保存一天
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    public void setIfAbsent(String key, Object value) {
        setIfAbsent(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return true 存在
     */
    public boolean isExists(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    /**
     * 获取RList对象
     *
     * @param key RList的key
     * @return RList对象
     */
    public <T> RList<T> getList(String key) {
        return redissonClient.getList(key);
    }

    /**
     * 获取RMapCache对象
     *
     * @param key
     * @return RMapCache对象
     */
    public <K, V> RMapCache<K, V> getMap(String key) {
        return redissonClient.getMapCache(key);
    }

    /**
     * 获取RSet对象
     *
     * @param key
     * @return RSET对象
     */
    public <T> RSet<T> getSet(String key) {
        return redissonClient.getSet(key);
    }

    /**
     * 获取RScoredSortedSet对象
     *
     * @param key
     * @param <T>
     * @return RScoredSortedSet对象
     */
    public <T> RScoredSortedSet<T> getScoredSortedSet(String key) {
        return redissonClient.getScoredSortedSet(key);
    }
}