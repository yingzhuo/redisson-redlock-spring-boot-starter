package com.github.yingzhuo.springboot.redlock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 红锁配置信息
 *
 * @author 应卓
 * @since 0.1.0
 */
@ConfigurationProperties(prefix = "red-lock")
public class RedLockProperties {

    /**
     * 本插件是否启用
     */
    private boolean enabled = true;

    /**
     * 红锁需要的Redis主节点
     */
    private List<Node> nodes = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
