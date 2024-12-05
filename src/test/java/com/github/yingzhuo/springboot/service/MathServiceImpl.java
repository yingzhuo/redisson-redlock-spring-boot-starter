package com.github.yingzhuo.springboot.service;

import com.github.yingzhuo.springboot.redlock.UseMultiLock;
import org.springframework.stereotype.Component;

@Component
public class MathServiceImpl implements MathService {

    @Override
    @UseMultiLock(value = "'my-lock' + #args[0] + #args[1]", usingSpEL = true)
    public int add(int a, int b) {
        return a + b;
    }

}
