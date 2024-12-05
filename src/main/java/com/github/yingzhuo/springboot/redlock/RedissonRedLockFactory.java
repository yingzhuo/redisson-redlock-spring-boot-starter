package com.github.yingzhuo.springboot.redlock;

import org.redisson.api.RLock;

import java.util.function.Function;

/**
 * @author 应卓
 * @since 0.1.0
 */
@FunctionalInterface
public interface RedissonRedLockFactory extends Function<String, RLock> {

    /**
     * 创建多联锁
     *
     * @param lockName 锁名称
     * @return 多联锁实例
     */
    public RLock createLock(String lockName);

    @Override
    public default RLock apply(String lockName) {
        return createLock(lockName);
    }

}
