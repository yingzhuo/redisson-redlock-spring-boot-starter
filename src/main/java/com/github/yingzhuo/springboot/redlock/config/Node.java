package com.github.yingzhuo.springboot.redlock.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * 节点配置信息
 *
 * @author 应卓
 * @since 0.1.0
 */
@Getter
@Setter
public class Node implements java.io.Serializable {

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
