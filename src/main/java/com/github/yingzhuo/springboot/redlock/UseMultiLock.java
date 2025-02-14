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

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 0.2.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseMultiLock {

	/**
	 * SpEL或锁的名称
	 *
	 * @return SpEL或锁的名称
	 */
	public String value() default "";

	/**
	 * value() 表达的含义是否为SpEL
	 *
	 * @return 如果本值为true，则value()为SpEL
	 * @see #value()
	 */
	public boolean usingSpEL() default true;

	/**
	 * 锁自动释放时间
	 *
	 * @return 自动释放时间
	 */
	public long leaseTime() default 30L;

	/**
	 * 锁自动释放时间单位
	 *
	 * @return 自动释放时间单位
	 */
	public TimeUnit leaseTimeUnit() default TimeUnit.SECONDS;

}
