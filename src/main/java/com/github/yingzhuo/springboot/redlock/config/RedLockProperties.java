package com.github.yingzhuo.springboot.redlock.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
public class RedLockProperties implements java.io.Serializable {

    /**
     * 本插件是否启用
     */
    private boolean enabled = true;

    /**
     * 红锁需要的Redis主节点
     */
    private List<Node> nodes;

}
