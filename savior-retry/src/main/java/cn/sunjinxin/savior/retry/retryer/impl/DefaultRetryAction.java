package cn.sunjinxin.savior.retry.retryer.impl;

import cn.sunjinxin.savior.retry.factory.strategy.RetryStrategy;
import cn.sunjinxin.savior.retry.factory.strategy.RetryType;
import cn.sunjinxin.savior.retry.function.RetryFunction;
import cn.sunjinxin.savior.retry.retryer.RetryAction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.TimeUnit;

/**
 * @author sunjinxin
 * @since 2023/11/14 14:32
 */
public class DefaultRetryAction implements RetryAction {

    @Override
    public RetryType getStrategy() {
        return RetryType.DEFAULT;
    }

    @Override
    public <T> T invoke(RetryFunction<Pair<Boolean, T>> retryFunction, RetryStrategy retryStrategy) {
        for (int i = 0; i < retryStrategy.getRetryCount(); i++) {
            try {
                Pair<Boolean, T> invoke = retryFunction.invoke();
                if (!invoke.getKey()) {
                    return invoke.getValue();
                }
                try {
                    TimeUnit.SECONDS.sleep(i);
                } catch (InterruptedException ignored) {
                }
            } catch (Throwable throwable) {
                try {
                    throwable.getClass().asSubclass(retryStrategy.getThrowable());
                } catch (ClassCastException e) {
                    return null;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(retryStrategy.getRetryWaitTime());
                } catch (InterruptedException ignored) {
                }
            }
        }
        return null;
    }
}
