package com.github.yingzhuo.springboot.redlock;

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
     * @param props 配置项
     * @return 工厂实例
     * @since 0.1.0
     */
    @Bean
    public RedissonRedLockFactory redissonRedLockFactory(RedLockProperties props) {
        return new RedissonRedLockFactoryImpl(props);
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
    @ConditionalOnProperty(prefix = "red-lock.aspect-advice", name = "enabled", havingValue = "true", matchIfMissing = true)
    public UseMultiLockAdvice useMultiLockAdvice(RedissonRedLockFactory lockFactory, RedLockProperties props) {
        var bean = new UseMultiLockAdvice();
        bean.setLockFactory(lockFactory);
        bean.setOrder(props.getAspectAdvice().getOrder());
        return bean;
    }

}
