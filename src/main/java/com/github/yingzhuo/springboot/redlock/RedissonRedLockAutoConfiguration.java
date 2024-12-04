/*
 ____          _ _                           ____          _ _            _        ____             _                   ____              _       ____  _             _
|  _ \ ___  __| (_)___ ___  ___  _ __       |  _ \ ___  __| | | ___   ___| | __   / ___| _ __  _ __(_)_ __   __ _      | __ )  ___   ___ | |_    / ___|| |_ __ _ _ __| |_ ___ _ __
| |_) / _ \/ _` | / __/ __|/ _ \| '_ \ _____| |_) / _ \/ _` | |/ _ \ / __| |/ /___\___ \| '_ \| '__| | '_ \ / _` |_____|  _ \ / _ \ / _ \| __|___\___ \| __/ _` | '__| __/ _ \ '__|
|  _ <  __/ (_| | \__ \__ \ (_) | | | |_____|  _ <  __/ (_| | | (_) | (__|   <_____|__) | |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____|__) | || (_| | |  | ||  __/ |
|_| \_\___|\__,_|_|___/___/\___/|_| |_|     |_| \_\___|\__,_|_|\___/ \___|_|\_\   |____/| .__/|_|  |_|_| |_|\__, |     |____/ \___/ \___/ \__|   |____/ \__\__,_|_|   \__\___|_|
                                                                                        |_|                 |___/
 */
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
