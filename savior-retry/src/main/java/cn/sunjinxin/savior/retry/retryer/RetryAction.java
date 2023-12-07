package cn.sunjinxin.savior.retry.retryer;

import cn.sunjinxin.savior.retry.factory.strategy.RetryStrategy;
import cn.sunjinxin.savior.retry.factory.strategy.RetryType;
import cn.sunjinxin.savior.retry.function.RetryFunction;
import org.apache.commons.lang3.tuple.Pair;

/**
 * retry api
 *
 * @author sunjinxin
 * @since 2023/11/14 14:11
 */
public interface RetryAction {

    /**
     *
     * @return /
     */
    RetryType getStrategy();

    /**
     *
     * @param retryFunction /
     * @param retryStrategy /
     * @return /
     */
    <T>T invoke(RetryFunction<Pair<Boolean, T>> retryFunction, RetryStrategy retryStrategy);



}
