package cn.sunjinxin.savior.ateye.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sunjinxin
 * @since 2024/1/8 20:49
 */
@ConfigurationProperties(prefix = "savior.ateye")
@Data
public class AteyeProperties {

    private String pathPrefix = "ateye";
}
