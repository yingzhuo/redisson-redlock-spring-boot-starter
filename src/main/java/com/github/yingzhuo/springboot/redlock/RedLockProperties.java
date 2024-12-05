package com.github.yingzhuo.springboot.redlock;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

import java.io.Serializable;
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
        if (nodes.size() == 0) {
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
