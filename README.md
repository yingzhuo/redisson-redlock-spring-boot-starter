# redisson-redlock-spring-boot-starter

### 依赖 (maven):

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>redisson-redlock-spring-boot-starter</artifactId>
    <scope>0.1.0</scope>
</dependency>
```

### 依赖 (gradle):

```groovy
implementation 'com.github.yingzhuo:redisson-redlock-spring-boot-starter:0.1.0'
```

### 使用

```yaml
# application.yaml
red-lock:
  enabled: true
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
        final RedissonMultiLock lock = lockFactory.createMultiLock("my-lock");

        lock.lock(10, TimeUnit.SECONDS);

        try {
            System.out.println("do some work.");
            System.out.println("do some work.");
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
