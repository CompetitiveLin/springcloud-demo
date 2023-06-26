package com.example.common.redis.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
import org.redisson.client.protocol.ScoredEntry;

import java.util.*;
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
     * 获取所有的指定前缀 keys
     */
    public Set<String> getKeys(String prefix) {
        Iterable<String> keysByPattern = getKeys().getKeysByPattern(prefix);
        Set<String> keys = new HashSet<>();
        for (String s : keysByPattern) {
            keys.add(s);
        }
        return keys;
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

    //===========  ZSet  ===========//

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


    /**
     * 新增ZSet元素,存在则刷新
     *
     */
    public <T> void zScoreAddAsync(String key, double score, T member) {
        zScoreAddAsync(key, score, member, DEFAULT_EXPIRE);
    }


    /**
     * 新增ZSet元素,存在则刷新
     *
     * @param expire 过期时间
     */
    public <T> void zScoreAddAsync(String key, double score, T member, long expire) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        scoredSortedSet.expire(Duration.ofSeconds(expire));
        scoredSortedSet.addAsync(score, member);
    }

    /**
     * 批量新增
     */
    public <T> void zScoreAddAsyncBatch(String key, Map<String, Double> map, long expire) {
        if (expire <= 0) {
            expire = DEFAULT_EXPIRE;
        }
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        scoredSortedSet.add(0, DEFAULT_EXPIRE);
        scoredSortedSet.expire(Duration.ofSeconds(expire));
        RBatch batch = redissonClient.createBatch();
        map.forEach((member, score) -> {
            batch.getScoredSortedSet(key).addAsync(score, member);
        });
        batch.execute();
    }

    /**
     * 读取指定 key 下所有 member, 按照 score 升序(默认)
     */
    public Collection<Object> getZSetMembers(String key, int startIndex, int endIndex) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        return scoredSortedSet.valueRange(startIndex, endIndex);
    }

    /**
     * 取指定 key 下所有 member, 按照 score 降序
     */
    public Collection<Object> getZSetMembersReversed(String key, int startIndex, int endIndex) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        return scoredSortedSet.valueRangeReversed(startIndex, endIndex);
    }

    /**
     * 读取 member和score, 按照 score 升序(默认)
     */
    public Collection<ScoredEntry<Object>> getZSetEntryRange(String key, int startIndex, int endIndex) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        return scoredSortedSet.entryRange(startIndex, endIndex);
    }


    /**
     * 读取 member和score, 按照 score 降序
     */
    public Collection<ScoredEntry<Object>> getZSetEntryRangeReversed(String key, int startIndex, int endIndex) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        return scoredSortedSet.entryRangeReversed(startIndex, endIndex);
    }

    /**
     * 读取指定 key 下 member 的 score
     * 返回 null 表示不存在
     */
    public Double getZSetMemberScore(String key, String member) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return null;
        }
        return scoredSortedSet.getScore(member);
    }


    /**
     * 读取指定 key 下 memberList 的 score
     * 返回null 表示不存在
     */
    public Double getZSetMemberScore(String key, List<String> memberList) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return null;
        }
        return scoredSortedSet.getScore(memberList);
    }

    /**
     * 读取指定 key 下 member 的 rank 排名(升序情况)
     * 返回null 表示不存在, 下标从0开始
     */
    public Integer getZSetMemberRank(String key, String member) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return null;
        }
        return scoredSortedSet.rank(member);
    }


    /**
     * 异步删除指定 ZSet 中的指定 memberName 元素
     */
    public void removeZSetMemberAsync(String key, String memberName) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return;
        }
        scoredSortedSet.removeAsync(memberName);
    }


    /**
     * 异步批量删除指定 ZSet 中的指定 member 元素列表
     */
    public void removeZSetMemberAsync(String key, List<String> memberList) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return;
        }
        RBatch batch = redissonClient.createBatch();
        memberList.forEach(member -> batch.getScoredSortedSet(key).removeAsync(member));
        batch.execute();
    }


    /**
     * 统计ZSet分数范围内元素总数. 区间包含分数本身
     * 注意这里不能用 -1 代替最大值
     */
    public int getZSetCountByScoresInclusive(String key, double startScore, double endScore) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return 0;
        }
        return scoredSortedSet.count(startScore, true, endScore, true);
    }

    /**
     * 返回ZSet分数范围内 member 列表. 区间包含分数本身.
     * 注意这里不能用 -1 代替最大值
     */
    public Collection<Object> getZSetMembersByScoresInclusive(String key, double startScore, double endScore) {
        RScoredSortedSet<Object> scoredSortedSet = getScoredSortedSet(key);
        if (!scoredSortedSet.isExists()) {
            return null;
        }
        return scoredSortedSet.valueRange(startScore, true, endScore, true);
    }
    
    
}