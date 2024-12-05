package com.github.yingzhuo.springboot.redlock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class UseMultiLockAdvice implements ApplicationContextAware {

    private RedissonRedLockFactory lockFactory = null;
    private ApplicationContext applicationContext = null;

    /**
     * 默认构造方法
     */
    public UseMultiLockAdvice() {
        super();
    }

    @Pointcut("@annotation(com.github.yingzhuo.springboot.redlock.UseMultiLock)")
    public void pc() {
    }

    @Around("pc()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        var annotation = getAnnotation(joinPoint);

        // 居然没有元注释，这不可能
        if (annotation == null) {
            return joinPoint.proceed();
        }

        var lockName = parseLockName(annotation, joinPoint.getArgs());

        var mLock = lockFactory.createMultiLock(lockName);

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

        var expression = annotation.lockName();

        if (!annotation.usingSpEL()) {
            // 非SpEL
            if (expression == null || expression.isBlank()) {
                throw new IllegalArgumentException("lockName is required");
            } else {
                return expression;
            }
        } else {
            // SpEL解析
            var parser = new SpelExpressionParser();
            var context = new StandardEvaluationContext();
            context.setVariable("args", args);
            context.setVariable("beans", applicationContext);
            return (String) parser.parseExpression(expression).getValue(context);
        }
    }

}
