package cn.sunjinxin.savior.lock.config;

import cn.sunjinxin.savior.lock.factory.strategy.LockStrategy;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LockProperties
 *
 * @author sunjinxin
 * @since 2023/11/15 13:20
 */
@ConfigurationProperties(prefix = "savior.lock")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LockProperties {
    LockStrategy strategy = LockStrategy.DEFAULT;
    String lockKeyPrefix = "savior_lock";
    Integer retryTime = 3;
    String host;
    String password;
    Integer port = 3306;
    Integer timeout = 5000;
    Integer soTimeout = 5000;
    Integer maxAttempts = 5;
}
