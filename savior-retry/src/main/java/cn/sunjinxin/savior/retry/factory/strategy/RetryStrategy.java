package cn.sunjinxin.savior.retry.factory.strategy;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeoutException;

/**
 * @author sunjinxin
 * @since 2023/11/14 14:04
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RetryStrategy {

    Integer retryCount;

    Long retryWaitTime;

    Class<? extends Throwable> throwable;
}
