package cn.sunjinxin.savior.retry.function;

/**
 * @author sunjinxin
 * @since 2023/11/14 17:24
 */
@FunctionalInterface
public interface RetryFunction<R> {

    /**
     * function
     *
     * @return /
     */
    R invoke();
}
