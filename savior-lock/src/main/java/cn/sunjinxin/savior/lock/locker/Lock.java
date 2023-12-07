package cn.sunjinxin.savior.lock.locker;

/**
 * lock api
 * @author sunjinxin
 * @since 2023/12/7 14:20
 */
public interface Lock {

    /**
     * try lock
     * @return /
     */
    boolean tryLock();

    /**
     * unlock
     */
    void unlock();
}
