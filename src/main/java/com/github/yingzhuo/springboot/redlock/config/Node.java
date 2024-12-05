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
