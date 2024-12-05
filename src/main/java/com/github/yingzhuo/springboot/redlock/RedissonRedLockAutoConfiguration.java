package com.github.yingzhuo.springboot.redlock;

import com.github.yingzhuo.springboot.redlock.config.RedLockProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置类
 *
 * @author 应卓
 * @since 0.1.0
 */
@EnableConfigurationProperties(RedLockProperties.class)
@ConditionalOnProperty(prefix = "red-lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedissonRedLockAutoConfiguration {

    @Bean
    public RedissonRedLockFactory redissonRedLockFactory(RedLockProperties properties) {
        return new RedissonRedLockFactoryImpl(properties);
    }

}
