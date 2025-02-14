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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

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
	public RedissonRedLockFactory redissonRedLockFactory(
		RedLockProperties props,
		@Nullable @Autowired(required = false) ServerConfigCustomizer customizer
	) {
		var bean = new RedissonRedLockFactoryImpl(props);
		bean.setServerConfigCustomizer(customizer);
		return bean;
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
	public UseMultiLockAdvice useMultiLockAdvice(
		RedissonRedLockFactory lockFactory,
		RedLockProperties props) {
		var bean = new UseMultiLockAdvice();
		bean.setLockFactory(lockFactory);
		bean.setOrder(props.getAspectAdvice().getOrder());
		return bean;
	}

}
