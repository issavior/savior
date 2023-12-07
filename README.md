# 官网
[轻量级分布式技术解决方案框架 - Savior](http://savior.sunjinxin.cn/)
# 介绍
[Savior](http://savior.sunjinxin.cn/)是一款轻量级分布式技术解决方案框架，接入和使用极其简单，5秒钟即可接入使用！

[Savior](http://savior.sunjinxin.cn/)框架亦如其名，为业务立心，为技术立命，为往圣继绝学，为万世开太平，站在巨人的肩膀，比肩神明！
# 依赖
中央仓库
```xml
<dependency>
    <groupId>cn.sunjinxin.savior</groupId>
    <artifactId>savior</artifactId>
    <version>1.0.4</version>
</dependency>
```
# 启动
在启动类上标注@Savior注解，即可启动Savior框架所有组件的功能。

```java
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import cn.sunjinxin.savior.core.anno.Savior;

/**
 * @author issavior
 * @date 1314/05/20 00:00:00
 */
@Savior
@SpringBootApplication
public class AppRun {
    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }
}
```

# 组件

Savior框架中的组件亦可以独立引入，目前支持的组件：

1. 事件总线
2. 重试机制
3. 分布式锁
4. 事件管道
5. 领域扩展
6. 工作流
7. 缓存
   ...
