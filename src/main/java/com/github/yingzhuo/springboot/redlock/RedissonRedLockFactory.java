/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
