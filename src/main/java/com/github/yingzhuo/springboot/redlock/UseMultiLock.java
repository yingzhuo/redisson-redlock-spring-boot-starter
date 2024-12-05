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

    public String lockName() default "";

    public boolean usingSpEL() default true;

    public long leaseTime() default 30L;

    public TimeUnit leaseTimeUnit() default TimeUnit.SECONDS;

}
