package com.github.yingzhuo.springboot.redlock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;

/**
 * 切面用于支持 {@link UseMultiLock}
 *
 * @author 应卓
 * @since 0.2.0
 */
@Aspect
public class UseMultiLockAdvice implements ApplicationContextAware, Ordered {

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

        var lockName = parseLockName(annotation, joinPoint.getArgs());
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
        var s = joinPoint.getSignature();
        if (s instanceof MethodSignature ms) {
            return ms.getMethod().getAnnotation(UseMultiLock.class);
        } else {
            return null;
        }
    }

    private String parseLockName(UseMultiLock annotation, Object[] args) {
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
            context.setVariable("args", args);

            return (String) new SpelExpressionParser()
                    .parseExpression(expression).getValue(context);
        }
    }

}
