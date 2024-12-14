package com.github.yingzhuo.springboot.redlock;

import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@link RedissonRedLockFactory} 实现
 *
 * @author 应卓
 * @since 0.1.0
 */
public class RedissonRedLockFactoryImpl implements RedissonRedLockFactory, InitializingBean {

    private static final String NODE_ADDRESS_PREFIX = "redis://";

    private final int size;
    private final List<RedLockProperties.Node> nodeConfigs;
    private final List<RedissonClient> clients;

    @Nullable
    private ServerConfigCustomizer serverConfigCustomizer;

    /**
     * 构造方法
     *
     * @param properties 配置项
     */
    public RedissonRedLockFactoryImpl(RedLockProperties properties) {
        this.size = properties.getNodes().size();
        this.nodeConfigs = properties.getNodes();
        this.clients = new ArrayList<>(properties.getNodes().size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        for (var node : nodeConfigs) {
            var conf = new Config();
            var singleServerConf = conf.useSingleServer();

            // clientName (optional)
            getClientName(node).ifPresent(singleServerConf::setClientName);

            // host + port
            singleServerConf.setAddress(getAddress(node));

            // username (optional)
            // empty string means null
            getUsername(node).ifPresent(singleServerConf::setUsername);

            // password (optional)
            // empty string means null
            getPassword(node).ifPresent(singleServerConf::setPassword);

            // database
            singleServerConf.setDatabase(getDatabase(node));

            // connection pool
            singleServerConf.setConnectionPoolSize(node.getConnectionPoolSize());
            singleServerConf.setConnectionMinimumIdleSize(node.getConnectionMinimumIdleSize());
            singleServerConf.setIdleConnectionTimeout((int) node.getIdleConnectionTimeout().toMillis());

            // connection
            singleServerConf.setConnectTimeout((int) node.getConnectTimeout().toMillis());
            singleServerConf.setTimeout((int) node.getTimeout().toMillis());

            // 客制化
            if (serverConfigCustomizer != null) {
                serverConfigCustomizer.custom(singleServerConf);
            }

            clients.add(Redisson.create(conf));
        }
    }

    public void setServerConfigCustomizer(@Nullable ServerConfigCustomizer serverConfigCustomizer) {
        this.serverConfigCustomizer = serverConfigCustomizer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RLock createLock(String lockName) {
        Assert.hasText("lockName", "lockName is required");

        if (size == 1) {
            return clients.get(0).getLock(lockName);
        }

        var subLocks = new RLock[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            var client = clients.get(i);
            subLocks[i] = client.getLock(lockName);
        }
        return new RedissonMultiLock(subLocks);
    }

    private String getAddress(RedLockProperties.Node node) {
        var address = node.getAddress();
        if (address == null) {
            throw new BeanCreationException("config error! address is required");
        }
        if (!address.startsWith(NODE_ADDRESS_PREFIX)) {
            address = NODE_ADDRESS_PREFIX + address;
        }
        return address;
    }

    private Optional<String> getUsername(RedLockProperties.Node node) {
        var username = node.getUsername();
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(username);
        }
    }

    private Optional<String> getPassword(RedLockProperties.Node node) {
        var pwd = node.getPassword();
        if (pwd == null || pwd.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(pwd);
        }
    }

    private Optional<String> getClientName(RedLockProperties.Node node) {
        var clientName = node.getClientName();
        if (clientName == null || clientName.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(clientName);
        }
    }

    private int getDatabase(RedLockProperties.Node node) {
        return node.getDatabase();
    }

}
