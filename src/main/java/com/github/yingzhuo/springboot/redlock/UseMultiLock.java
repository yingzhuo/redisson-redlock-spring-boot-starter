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
