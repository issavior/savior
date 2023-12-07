package cn.sunjinxin.savior.retry.config;

import cn.sunjinxin.savior.retry.factory.strategy.RetryType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author sunjinxin
 * @since 2023/11/14 15:03
 */
@ConfigurationProperties(prefix = "savior.retry")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RetryProperties {

    @NestedConfigurationProperty
    RetryType type = RetryType.DEFAULT;

}
