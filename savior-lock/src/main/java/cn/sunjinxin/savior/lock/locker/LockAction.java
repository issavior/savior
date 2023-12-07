package cn.sunjinxin.savior.lock.locker;

import cn.sunjinxin.savior.lock.factory.strategy.LockStrategy;

/**
 * @author sunjinxin
 * @since 2023/11/15 14:09
 */
public interface LockAction {

    /**
     * strategy
     *
     * @return /
     */
    LockStrategy getStrategy();

    /**
     * lock
     *
     * @param lockKey /
     * @return /
     */
    Lock lock(String lockKey);
}
