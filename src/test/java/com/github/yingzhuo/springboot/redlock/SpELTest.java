package com.github.yingzhuo.springboot.redlock;

import org.junit.jupiter.api.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {

    @Test
    public void test() {
        var parser = new SpelExpressionParser();

        var context = new StandardEvaluationContext();
        context.setVariable("args", new Object[]{"MyLock"});

        var obj = parser.parseExpression("'MyPrefix-' + #args[0]").getValue(context);
        System.out.println(obj);
    }

}
