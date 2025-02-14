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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 切面用于支持 {@link UseMultiLock}
 *
 * @author 应卓
 * @since 0.2.0
 */
@Aspect
public class UseMultiLockAdvice implements ApplicationContextAware, Ordered {

	/**
	 * 表达式解析器
	 */
	private static final ExpressionParser EXPRESSION_PARSER;

	static {
		var config = new SpelParserConfiguration(true, true);
		EXPRESSION_PARSER = new SpelExpressionParser(config);
	}

	private RedissonRedLockFactory lockFactory = null;
	private ApplicationContext applicationContext = null;
	private int order = Ordered.LOWEST_PRECEDENCE;

	/**
	 * 默认构造方法
	 */
	public UseMultiLockAdvice() {
		super();
	}

	@Around("@annotation(com.github.yingzhuo.springboot.redlock.UseMultiLock)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		var annotation = getAnnotation(joinPoint);

		// 居然没有元注释，这不可能
		if (annotation == null) {
			return joinPoint.proceed();
		}

		var lockName = parseLockName(annotation, joinPoint);
		var mLock = lockFactory.createLock(lockName);

		// 加锁
		mLock.lock(annotation.leaseTime(), annotation.leaseTimeUnit());

		try {
			return joinPoint.proceed();
		} finally {
			// 解锁
			if (mLock.isHeldByCurrentThread()) {
				mLock.unlock();
			}
		}
	}

	public void setLockFactory(RedissonRedLockFactory lockFactory) {
		this.lockFactory = lockFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Nullable
	private UseMultiLock getAnnotation(ProceedingJoinPoint joinPoint) {
		return getMethod(joinPoint).getAnnotation(UseMultiLock.class);
	}

	private String parseLockName(UseMultiLock annotation, ProceedingJoinPoint joinPoint) {
		var expression = annotation.value();

		if (!annotation.usingSpEL()) {
			// 非SpEL
			if (expression == null || expression.isBlank()) {
				throw new IllegalArgumentException("lockName is required");
			} else {
				return expression;
			}
		} else {
			// SpEL解析
			var context = new StandardEvaluationContext(applicationContext);
			context.setVariable("args", joinPoint.getArgs());
			context.setVariable("method", getMethod(joinPoint).getName());

			return (String) EXPRESSION_PARSER
				.parseExpression(expression).getValue(context);
		}
	}

	private Method getMethod(ProceedingJoinPoint joinPoint) {
		var s = joinPoint.getSignature();
		if (s instanceof MethodSignature ms) {
			return ms.getMethod();
		} else {
			throw new AssertionError();
		}
	}

}
