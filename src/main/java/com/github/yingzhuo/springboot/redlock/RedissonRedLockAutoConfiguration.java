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

    /**
     * 注册锁的工厂
     *
     * @param properties 配置项
     * @return 工厂实例
     * @since 0.1.0
     */
    @Bean
    public RedissonRedLockFactory redissonRedLockFactory(RedLockProperties properties) {
        return new RedissonRedLockFactoryImpl(properties);
    }

    /**
     * 注册切面用于支持{@link UseMultiLock}
     *
     * @param lockFactory 锁的工厂
     * @return 切面实例
     * @see UseMultiLock
     * @since 0.2.0
     */
    @Bean
    public UseMultiLockAdvice useMultiLockAdvice(RedissonRedLockFactory lockFactory) {
        var bean = new UseMultiLockAdvice();
        bean.setLockFactory(lockFactory);
        return bean;
    }

}
