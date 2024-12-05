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
