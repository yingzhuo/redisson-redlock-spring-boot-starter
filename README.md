# redisson-redlock-spring-boot-starter

[![Maven Central](https://img.shields.io/maven-central/v/com.github.yingzhuo/redisson-redlock-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.yingzhuo%22%20AND%20a:%22redisson-redlock-spring-boot-starter%22)

### 依赖 (maven):

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>redisson-redlock-spring-boot-starter</artifactId>
    <scope>1.1.0</scope>
</dependency>
```

### 依赖 (gradle):

```groovy
implementation 'com.github.yingzhuo:redisson-redlock-spring-boot-starter:1.1.0'
```

### 使用

```yaml
# application.yaml
red-lock:
  enabled: true
  allow-downgrade-to-non-multi-lock: true
  register-aop-aspect-advice: true
  nodes:
    - address: "127.0.0.1:6379"
      username: "root"
      password: "root"
    - address: "127.0.0.1:6380"
      username: "root"
      password: "root"
    - address: "127.0.0.1:6381"
      username: "root"
      password: "root"
```

```java
@SpringBootTest(classes = TestApplication.class)
public class RedissonRedLockFactoryTest {

    @Autowired
    private RedissonRedLockFactory lockFactory;

    @Test
    public void test() {
        var lock = lockFactory.createMultiLock("my-lock");

        lock.lock(10, TimeUnit.SECONDS);

        try {
            System.out.println("do some work.");
            System.out.println("do some work.");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
```

### 许可证

* [Apache-2.0](./LICENSE.txt)
