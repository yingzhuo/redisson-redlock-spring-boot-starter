/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.springboot.redlock;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 红锁配置信息
 *
 * @author 应卓
 * @since 0.1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "red-lock")
public class RedLockProperties implements InitializingBean, Serializable {

	/**
	 * 本插件是否启用
	 */
	private boolean enabled = true;

	/**
	 * 当只配置一个节点时是否允许降级成非多联锁
	 */
	private boolean allowDowngradeToNonMultiLock = false;

	/**
	 * 切面配置
	 */
	private AspectAdvice aspectAdvice = new AspectAdvice();

	/**
	 * 红锁需要的Redis主节点
	 */
	private List<Node> nodes = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() {
		if (nodes.isEmpty()) {
			throw new IllegalArgumentException("config error! node not defined");
		}

		if (nodes.size() == 1 && !allowDowngradeToNonMultiLock) {
			throw new IllegalArgumentException("config error! just one node defined");
		}
	}

	/**
	 * 节点配置项
	 */
	@Getter
	@Setter
	public static class Node implements Serializable {

		/**
		 * 节点名称
		 */
		@Nullable
		private String clientName;

		/**
		 * 节点地址 (例: redis://127.0.0.1:6379)
		 */
		@Nullable
		private String address = null;

		/**
		 * 用户名 (例 root)
		 */
		@Nullable
		private String username = null;

		/**
		 * 用户名 (例 password)
		 */
		@Nullable
		private String password = null;

		/**
		 * database
		 */
		private int database = 0;

		/**
		 * Minimum idle Redis connection amount
		 */
		private int connectionMinimumIdleSize = 24;

		/**
		 * Redis connection maximum pool size
		 */
		private int connectionPoolSize = 64;

		/**
		 * If pooled connection not used for a <code>timeout</code> time
		 * and current connections amount bigger than minimum idle connections pool size,
		 * then it will be closed and removed from pool.
		 * Value in milliseconds.
		 */
		private Duration idleConnectionTimeout = Duration.ofMillis(10000L);

		/**
		 * Timeout during connecting to any Redis server.
		 * Value in milliseconds.
		 */
		private Duration connectTimeout = Duration.ofMillis(10000L);

		/**
		 * Redis server response timeout. Starts to countdown when Redis command was successfully sent.
		 * Value in milliseconds.
		 */
		private Duration timeout = Duration.ofMillis(3000L);
	}

	/**
	 * 切面相关配置
	 */
	@Getter
	@Setter
	public static class AspectAdvice implements Serializable {

		/**
		 * 是否启用AOP切面
		 */
		private boolean enabled = true;

		/**
		 * 顺序
		 */
		private int order = Ordered.LOWEST_PRECEDENCE;
	}

}
