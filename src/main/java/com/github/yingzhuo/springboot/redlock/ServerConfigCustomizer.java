package com.github.yingzhuo.springboot.redlock;

import org.redisson.config.SingleServerConfig;

/**
 * @author 应卓
 * @since 1.0.1
 */
@FunctionalInterface
public interface ServerConfigCustomizer {

    /**
     * 客制
     *
     * @param config 配置
     */
    public void custom(SingleServerConfig config);

}
