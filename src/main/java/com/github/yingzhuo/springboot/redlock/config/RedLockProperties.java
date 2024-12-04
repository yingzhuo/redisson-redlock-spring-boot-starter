/*
 ____          _ _                           ____          _ _            _        ____             _                   ____              _       ____  _             _
|  _ \ ___  __| (_)___ ___  ___  _ __       |  _ \ ___  __| | | ___   ___| | __   / ___| _ __  _ __(_)_ __   __ _      | __ )  ___   ___ | |_    / ___|| |_ __ _ _ __| |_ ___ _ __
| |_) / _ \/ _` | / __/ __|/ _ \| '_ \ _____| |_) / _ \/ _` | |/ _ \ / __| |/ /___\___ \| '_ \| '__| | '_ \ / _` |_____|  _ \ / _ \ / _ \| __|___\___ \| __/ _` | '__| __/ _ \ '__|
|  _ <  __/ (_| | \__ \__ \ (_) | | | |_____|  _ <  __/ (_| | | (_) | (__|   <_____|__) | |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____|__) | || (_| | |  | ||  __/ |
|_| \_\___|\__,_|_|___/___/\___/|_| |_|     |_| \_\___|\__,_|_|\___/ \___|_|\_\   |____/| .__/|_|  |_|_| |_|\__, |     |____/ \___/ \___/ \__|   |____/ \__\__,_|_|   \__\___|_|
                                                                                        |_|                 |___/
 */
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

    public List<Node> getNodes() {
        return nodes;
    }

}
