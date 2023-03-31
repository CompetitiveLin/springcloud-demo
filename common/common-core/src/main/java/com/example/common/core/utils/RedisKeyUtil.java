package com.example.common.core.utils;



public final class RedisKeyUtil {

    /**
     * 验证码Key
     */
    public static String getUserCaptchaKey(String uuid) {
        return "sys:user:captcha:" + uuid;
    }

    public static String getResourceTreeKey(Long userId) {
        return "sys:resource:tree:" + userId;
    }

    /**
     * 用户信息Key
     */
    public static String getUserInfoKey(String token) {
        return "sys:user:info:" + token;
    }

    /**
     * 二级缓存Key
     */
    public static String getDataCacheKey(String name,Long id) {
        return "sys:" + name + ":cache:" + id;
    }

    /**
     * 布隆过滤器Key
     */
    public static String getBloomFilterKey() {
        return "sys:bloom:filter";
    }

    /**
     * OSS配置Key
     */
    public static String getOssConfigKey(Long tenantId) {
        return "sys:oss:config:" + tenantId;
    }

    /**
     * 消息消费Key
     * @return
     */
    public static String getMessageConsumeKey() {
        return "sys:message:consume";
    }

    /**
     * 全量同步索引Key
     * @return
     */
    public static String getSyncIndexKey(String code) {
        return "sys:resource:sync:" + code;
    }

    /**
     * 增量同步索引Key
     * @return
     */
    public static String getSyncIndexIncrementKey(String code) {
        return "sys:resource:sync:" + code + ":increment";
    }

    /**
     * 未读消息key
     * @param userId
     * @return
     */
    public static String getMessageUnReadKey(Long userId) {
        return "sys:message:unread:" + userId;
    }

    /**
     * 账号踢出Key
     */
    public static String getAccountKillKey(String token) {
        return "sys:account:kill:" + token;
    }

    /**
     * 手机验证码Key
     * @param mobile
     * @return
     */
    public static String getMobileCodeKey(String mobile) {
        return getUserCaptchaKey(mobile);
    }

    /**
     * 邮箱验证码Key
     * @param mail
     * @return
     */
    public static String getMailCodeKey(String mail) {
        return getUserCaptchaKey(mail);
    }

}
