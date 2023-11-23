package cn.sunjinxin.savior.event.configuration;

import cn.sunjinxin.savior.event.constant.EventStrategy;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * EventProperties
 *
 * @author issavior
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "savior.event")
public class EventProperties {

    /**
     * @see EventStrategy
     */
    EventStrategy strategy = EventStrategy.DEFAULT;

    /**
     * @see ThreadPoolProperties
     */
    ThreadPoolProperties asyncThreadPool = new ThreadPoolProperties();

}
