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
    <version>1.0.6</version>
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

| 组件                                                | 说明    | 文档                                | 导航                                                                       |
|---------------------------------------------------|-------|-----------------------------------|--------------------------------------------------------------------------|
| **[savior-ext](savior-ext.md)**                   | 扩展点   | [《扩展点文档》](savior-ext.md)          | [https://github.com/issavior/savior](https://github.com/issavior/savior) |   
| **[savior-mq](savior-mq.md)**                     | 消息队列  | [《消息队列文档》](savior-mq.md)          | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-toc](savior-toc.md)**                   | 超时中心  | [《超时中心文档》](savior-toc.md)         | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-rule](savior-rule.md)**                 | 规则引擎  | [《规则引擎文档》](savior-rule.md)        | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-lock](savior-lock.md)**                 | 分布式锁  | [《分布式锁文档》](savior-lock.md)        | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-core](savior-core.md)**                 | 核心组件  | [《核心组件文档》](savior-core.md)        | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-retry](savior-retry.md)**               | 重试机制  | [《重试机制文档》](savior-retry.md)       | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-trace](savior-trace.md)**               | 链路追踪  | [《链路追踪文档》](savior-trace.md)       | [https://github.com/issavior/savior](https://github.com/issavior/savior) |  
| **[savior-ateye](savior-ateye.md)**               | 服务穿透  | [《服务穿透文档》](savior-ateye.md)       | [https://github.com/issavior/savior](https://github.com/issavior/savior) |   
| **[savior-event](savior-event.md)**               | 事件总线  | [《事件总线文档》](savior-event.md)       | [https://github.com/issavior/savior](https://github.com/issavior/savior) |   
| **[savior-cache](savior-cache.md)**               | 多级缓存  | [《多级缓存文档》](savior-cache.md)       | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-example](savior-example.md)**           | 示例    | [《示例文档》](savior-example.md)       | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-workflow](savior-workflow.md)**         | 工作流   | [《工作流文档》](savior-workflow.md)     | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-sequence](savior-sequence.md)**         | 分布式ID | [《分布式ID文档》](savior-sequence.md)   | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| **[savior-statemachine](savior-statemachine.md)** | 状态机   | [《状态机文档》](savior-statemachine.md) | [https://github.com/issavior/savior](https://github.com/issavior/savior) | 
| ...                                               | ...   | ...                               | ...                                                                      | 


