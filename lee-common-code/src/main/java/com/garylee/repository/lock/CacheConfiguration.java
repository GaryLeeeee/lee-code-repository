package com.garylee.repository.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: GaryLeeeee
 * @date: 2021-08-09 22:45
 * @description: 缓存配置类
 **/
@Configuration
public class CacheConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * 单机模式 redisson 客户端
     *
     * @return
     */
    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress("redis://" + host + ":" + port);

        if (password != null && password.length() > 0) {
            singleServerConfig.setPassword(password);
        }

        return Redisson.create(config);
    }

    @Bean
    public DistributedLocker initDistributedLocker(RedissonClient redissonClient) {
        RedissonDistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);

        RedissonLockUtil.setRedissonLock(locker);
        return locker;
    }
}
