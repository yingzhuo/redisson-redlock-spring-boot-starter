package com.github.yingzhuo.springboot.redlock;

import com.github.yingzhuo.springboot.redlock.config.Node;
import com.github.yingzhuo.springboot.redlock.config.RedLockProperties;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 0.1.0
 */
public class RedissonRedLockFactoryImpl implements RedissonRedLockFactory, InitializingBean {

    private final List<Node> nodeConfigs;
    private final List<RedissonClient> clients;

    public RedissonRedLockFactoryImpl(RedLockProperties properties) {
        this.nodeConfigs = properties.getNodes();
        this.clients = new ArrayList<>(properties.getNodes().size());
    }

    @Override
    public void afterPropertiesSet() {
        for (var node : nodeConfigs) {
            var conf = new Config();
            var singleServerConf = conf.useSingleServer();

            // host + port
            singleServerConf.setAddress(getAddress(node));

            // username (optional)
            // empty string means null
            getUsername(node).ifPresent(singleServerConf::setUsername);

            // password (optional)
            // empty string means null
            getPassword(node).ifPresent(singleServerConf::setPassword);

            clients.add(Redisson.create(conf));
        }
    }

    @Override
    public RedissonMultiLock createMultiLock(String lockName) {
        var subLocks = new RLock[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            var client = clients.get(i);
            subLocks[i] = client.getLock(lockName);
        }
        return new RedissonMultiLock(subLocks);
    }

    @Override
    @Deprecated
    public RedissonRedLock createRedLock(String lockName) {
        var subLocks = new RLock[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            var client = clients.get(i);
            subLocks[i] = client.getLock(lockName);
        }
        return new RedissonRedLock(subLocks);
    }

    private String getAddress(Node node) {
        var address = node.getAddress();
        if (address == null) {
            throw new BeanCreationException("config error! address is required");
        }
        if (!address.startsWith("redis://")) {
            address = "redis://" + address;
        }
        return address;
    }

    private Optional<String> getUsername(Node node) {
        var username = node.getUsername();
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(username);
        }
    }

    private Optional<String> getPassword(Node node) {
        var pwd = node.getPassword();
        if (pwd == null || pwd.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(pwd);
        }
    }

}