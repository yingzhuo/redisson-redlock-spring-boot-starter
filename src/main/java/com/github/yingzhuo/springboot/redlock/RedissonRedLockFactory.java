/*
 ____          _ _                           ____          _ _            _        ____             _                   ____              _       ____  _             _
|  _ \ ___  __| (_)___ ___  ___  _ __       |  _ \ ___  __| | | ___   ___| | __   / ___| _ __  _ __(_)_ __   __ _      | __ )  ___   ___ | |_    / ___|| |_ __ _ _ __| |_ ___ _ __
| |_) / _ \/ _` | / __/ __|/ _ \| '_ \ _____| |_) / _ \/ _` | |/ _ \ / __| |/ /___\___ \| '_ \| '__| | '_ \ / _` |_____|  _ \ / _ \ / _ \| __|___\___ \| __/ _` | '__| __/ _ \ '__|
|  _ <  __/ (_| | \__ \__ \ (_) | | | |_____|  _ <  __/ (_| | | (_) | (__|   <_____|__) | |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____|__) | || (_| | |  | ||  __/ |
|_| \_\___|\__,_|_|___/___/\___/|_| |_|     |_| \_\___|\__,_|_|\___/ \___|_|\_\   |____/| .__/|_|  |_|_| |_|\__, |     |____/ \___/ \___/ \__|   |____/ \__\__,_|_|   \__\___|_|
                                                                                        |_|                 |___/
 */
package com.github.yingzhuo.springboot.redlock;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;

/**
 * @author 应卓
 * @since 0.1.0
 */
public interface RedissonRedLockFactory {

    /**
     * 创建多联锁
     *
     * @param lockName 锁名称
     * @return 多联锁实例
     */
    public RedissonMultiLock createMultiLock(String lockName);

    /**
     * 创建红锁
     *
     * @param lockName 锁名称
     * @return 红锁实例
     * @deprecated 请使用 {@link #createMultiLock(String)} 代替
     */
    @Deprecated
    public RedissonRedLock createRedLock(String lockName);

}
