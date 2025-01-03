package com.github.yingzhuo.springboot.redlock;

import com.github.yingzhuo.springboot.TestApplication;
import com.github.yingzhuo.springboot.service.MathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = TestApplication.class)
public class RedissonRedLockFactoryTest {

    @Autowired
    private RedissonRedLockFactory lockFactory;

    @Autowired
    private MathService mathService;

    @Test
    public void test1() {
        var lock = lockFactory.createLock("my-lock");

        lock.lock(10, TimeUnit.SECONDS);

        try {
            System.out.println("do some work.");
            System.out.println("do some work.");
            System.out.println("do some work.");
            System.out.println("do some work.");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Test
    public void test2() {
        System.out.println(mathService.add(100, 100));
    }

}
