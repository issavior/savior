package cn.sunjinxin.savior.retry;

import cn.sunjinxin.savior.retry.factory.RetryFactory;
import cn.sunjinxin.savior.retry.factory.strategy.RetryStrategy;
import cn.sunjinxin.savior.retry.function.RetryFunction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * retry api
 *
 * @author sunjinxin
 * @since 2023/11/14 13:53
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Retryer {

    RetryFactory retryFactory;
    static Integer RETRY_COUNT = 3;
    static Long RETRY_WAIT_TIME = 1000L;

    public <R> R invoke(RetryFunction<Pair<Boolean, R>> retryFunction) {
        return retryFactory.build().invoke(retryFunction,
                RetryStrategy.builder()
                        .retryCount(RETRY_COUNT)
                        .retryWaitTime(RETRY_WAIT_TIME)
                        .throwable(Exception.class)
                        .build());
    }

    public <R> R invoke(RetryFunction<Pair<Boolean, R>> retryFunction, RetryStrategy retryStrategy) {
        return retryFactory.build().invoke(retryFunction,
                RetryStrategy.builder()
                        .retryCount(Optional.ofNullable(retryStrategy.getRetryCount()).orElse(RETRY_COUNT))
                        .retryWaitTime(Optional.ofNullable(retryStrategy.getRetryWaitTime()).orElse(RETRY_WAIT_TIME))
                        .throwable(retryStrategy.getThrowable() == null ? Exception.class : retryStrategy.getThrowable())
                        .build());
    }

    public <R> R invoke(RetryFunction<Pair<Boolean, R>> retryFunction, Supplier<? extends RuntimeException> exceptionSupplier) {
        return Optional.ofNullable(invoke(retryFunction)).orElseThrow(exceptionSupplier);
    }

    public <R> R invoke(RetryFunction<Pair<Boolean, R>> retryFunction, RetryStrategy retryStrategy, Supplier<? extends RuntimeException> exceptionSupplier) {
        return Optional.ofNullable(invoke(retryFunction, retryStrategy)).orElseThrow(exceptionSupplier);
    }

    public <R> R invoke(RetryFunction<Pair<Boolean, R>> retryFunction, Supplier<? extends RuntimeException> exceptionSupplier, Supplier<?> logSuccess, Supplier<?> logFail) {
        try {
            return Optional.ofNullable(invoke(retryFunction)).orElseThrow(exceptionSupplier);
        } finally {
            if (Objects.isNull(invoke(retryFunction))) {
                logFail.get();
            } else {
                logSuccess.get();
            }
        }
    }

    public <R> R invoke(RetryFunction<Pair<Boolean, R>> retryFunction, RetryStrategy retryStrategy, Supplier<? extends RuntimeException> exceptionSupplier, Supplier<?> logSuccess, Supplier<?> logFail) {
        try {
            return Optional.ofNullable(invoke(retryFunction, retryStrategy)).orElseThrow(exceptionSupplier);
        } finally {
            if (Objects.isNull(invoke(retryFunction))) {
                logFail.get();
            } else {
                logSuccess.get();
            }
        }
    }
}
