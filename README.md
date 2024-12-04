# redisson-redlock-spring-boot-starter

### 依赖 (maven):

``xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>redisson-redlock-spring-boot-starter</artifactId>
    <scope>0.1.0</scope>
</dependency>
``

### 依赖 (gradle):

```groovy
implementation 'com.github.yingzhuo:redisson-redlock-spring-boot-starter:0.1.0'
```

### 使用 (application.yaml):

```yaml
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

### 许可证

* [Apache-2.0](./LICENSE.txt)
