## redisson-redlock-spring-boot-starter

[![License](https://img.shields.io/badge/License-Apache%20v2.0-red?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![Author](https://img.shields.io/badge/yingzhor%40gmail.com-F0FF00?style=flat-square)](mailto:yingzhor@gmail.com)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.yingzhuo/redisson-redlock-spring-boot-starter.svg?label=Maven%20Central&style=flat-square)](https://search.maven.org/search?q=g:%22com.github.yingzhuo%22%20AND%20a:%22redisson-redlock-spring-boot-starter%22)

### 依赖 (gradle):

```groovy
implementation 'com.github.yingzhuo:redisson-redlock-spring-boot-starter:${version}'
```

### 依赖 (maven):

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>redisson-redlock-spring-boot-starter</artifactId>
    <scope>${version}</scope>
</dependency>
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
