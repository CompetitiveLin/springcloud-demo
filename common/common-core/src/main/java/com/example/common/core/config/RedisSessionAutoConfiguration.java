package com.example.common.core.config;

import com.example.common.core.utils.RedisKeyUtil;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@ConditionalOnClass(Redisson.class)
@AutoConfigureBefore({RedisAutoConfiguration.class})
@EnableConfigurationProperties(RedisProperties.class)
public class RedisSessionAutoConfiguration {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";

    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Bean
    public RBloomFilter<String> bloomFilter(RedissonClient redisson) {
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter(RedisKeyUtil.getBloomFilterKey());
        bloomFilter.tryInit(10000,0.01);
        return bloomFilter;
    }

    /**
     * redisson配置
     * @param properties redis配置文件
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisClient(RedisProperties properties) {
        Config config;
        final Duration duration = properties.getTimeout();
        int timeout = duration == null ? 0 : (int) duration.toMillis();
        if (properties.getSentinel() != null) {
            config = new Config();
            config.useSentinelServers()
                    .setMasterName(properties.getSentinel().getMaster())
                    .addSentinelAddress(convertNodes(properties.isSsl(),properties.getSentinel().getNodes()))
                    .setDatabase(properties.getDatabase())
                    .setConnectTimeout(timeout)
                    .setPassword(properties.getPassword());
        } else if (properties.getCluster() != null) {
            config = new Config();
            config.useClusterServers()
                    .addNodeAddress(convertNodes(properties.isSsl(),properties.getCluster().getNodes()))
                    .setPassword(properties.getPassword())
                    .setTimeout(timeout);
        } else {
            config = new Config();
            config.useSingleServer()
                    .setAddress(convertAddress(properties.isSsl(),properties.getHost() ,properties.getPort()))
                    .setDatabase(properties.getDatabase())
                    .setPassword(properties.getPassword())
                    .setTimeout(timeout);
        }
        // 使用json序列化方式
        Codec codec = CustomJsonJacksonCodec.INSTANCE;
        config.setCodec(codec);
        return Redisson.create(config);
    }

    private String getProtocolPrefix(boolean isSsl) {
        return isSsl ? REDISS_PROTOCOL_PREFIX : REDIS_PROTOCOL_PREFIX;
    }

    private String convertAddress(boolean isSsl,String host,int port) {
        return getProtocolPrefix(isSsl) + host + ":" + port;
    }

    private String[] convertNodes(boolean isSsl,List<String> nodeList) {
        List<String> nodes = new ArrayList<>(nodeList.size());
        for (String node : nodeList) {
            if (node.startsWith(REDISS_PROTOCOL_PREFIX) || node.startsWith(REDIS_PROTOCOL_PREFIX)) {
                nodes.add(node);
            } else {
                nodes.add(getProtocolPrefix(isSsl) + node);
            }
        }
        return nodes.toArray(new String[0]);
    }

}