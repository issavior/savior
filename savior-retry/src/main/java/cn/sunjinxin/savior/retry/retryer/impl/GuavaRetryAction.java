package cn.sunjinxin.savior.retry.retryer.impl;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import cn.sunjinxin.savior.retry.factory.strategy.RetryStrategy;
import cn.sunjinxin.savior.retry.factory.strategy.RetryType;
import cn.sunjinxin.savior.retry.function.RetryFunction;
import cn.sunjinxin.savior.retry.retryer.RetryAction;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author sunjinxin
 * @since 2023/11/14 14:32
 */
public class GuavaRetryAction implements RetryAction {

    @Override
    public RetryType getStrategy() {
        return RetryType.GUAVA;
    }

    @Override
    public <T> T invoke(RetryFunction<Pair<Boolean, T>> retryFunction, RetryStrategy retryStrategy) {
        try {
            return RetryerBuilder.<Pair<Boolean, T>>newBuilder()
                    .withWaitStrategy(WaitStrategies.fixedWait(retryStrategy.getRetryWaitTime(), TimeUnit.MILLISECONDS))
                    .withStopStrategy(StopStrategies.stopAfterAttempt(retryStrategy.getRetryCount()))
                    .retryIfResult(result -> BooleanUtils.isTrue(result.getKey()))
                    .retryIfExceptionOfType(retryStrategy.getThrowable())
                    .build()
                    .call(retryFunction::invoke)
                    .getValue();
        } catch (ExecutionException | RetryException e) {
            return null;
        }
    }

}
