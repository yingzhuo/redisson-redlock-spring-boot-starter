/*
 ____          _ _                           ____          _ _            _        ____             _                   ____              _       ____  _             _
|  _ \ ___  __| (_)___ ___  ___  _ __       |  _ \ ___  __| | | ___   ___| | __   / ___| _ __  _ __(_)_ __   __ _      | __ )  ___   ___ | |_    / ___|| |_ __ _ _ __| |_ ___ _ __
| |_) / _ \/ _` | / __/ __|/ _ \| '_ \ _____| |_) / _ \/ _` | |/ _ \ / __| |/ /___\___ \| '_ \| '__| | '_ \ / _` |_____|  _ \ / _ \ / _ \| __|___\___ \| __/ _` | '__| __/ _ \ '__|
|  _ <  __/ (_| | \__ \__ \ (_) | | | |_____|  _ <  __/ (_| | | (_) | (__|   <_____|__) | |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____|__) | || (_| | |  | ||  __/ |
|_| \_\___|\__,_|_|___/___/\___/|_| |_|     |_| \_\___|\__,_|_|\___/ \___|_|\_\   |____/| .__/|_|  |_|_| |_|\__, |     |____/ \___/ \___/ \__|   |____/ \__\__,_|_|   \__\___|_|
                                                                                        |_|                 |___/
 */
package com.github.yingzhuo.springboot.redlock.config;

import org.springframework.lang.Nullable;

/**
 * 节点配置信息
 *
 * @author 应卓
 * @since 0.1.0
 */
public class Node {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
