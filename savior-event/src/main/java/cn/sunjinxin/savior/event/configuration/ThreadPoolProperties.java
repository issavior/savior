package cn.sunjinxin.savior.event.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * ThreadPoolProperties
 *
 * @author issavior
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadPoolProperties {

    Integer corePoolSize = 20;

    Integer maxPoolSize = 50;

    Integer queueCapacity = 1000;

    String threadNamePrefix = "thread-pool-savior-async-event-";

}
