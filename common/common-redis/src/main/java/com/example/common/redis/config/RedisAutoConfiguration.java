package com.example.common.redis.config;

import com.example.common.redis.utils.RedisKeyUtil;
import com.example.common.redis.utils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 */
@Configuration
@AutoConfigureAfter({RedisSessionAutoConfiguration.class})
@Import({RedisUtil.class, RedisKeyUtil.class})
public class RedisAutoConfiguration {

    /**
     * 自定义RedisTemplate
     * @return
     */
    @Bean("redisTemplate")
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<String, Object> redisTemplate(@Autowired(required = false) LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = getJsonRedisSerializer();
        // string序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash-key
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash-value
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private Jackson2JsonRedisSerializer getJsonRedisSerializer() {
        // Json序列化配置
        ObjectMapper objectMapper = CustomJsonJacksonCodec.getObjectMapper();
        return new Jackson2JsonRedisSerializer(objectMapper,Object.class);
    }

}