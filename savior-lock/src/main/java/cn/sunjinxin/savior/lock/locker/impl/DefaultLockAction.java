package cn.sunjinxin.savior.lock.locker.impl;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.lock.config.LockClient;
import cn.sunjinxin.savior.lock.config.LockProperties;
import cn.sunjinxin.savior.lock.factory.strategy.LockStrategy;
import cn.sunjinxin.savior.lock.locker.Lock;
import cn.sunjinxin.savior.lock.locker.LockAction;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * DefaultLockAction
 *
 * @author sunjinxin
 * @since 2023/11/15 14:27
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultLockAction implements LockAction, InitializingBean {

    static String LOCK_SPLIT = "_";
    static int LOCK_TIME_OUT_SECOND = 60;

    @NonFinal
    static DefaultLockAction LOCKER;
    @NonFinal
    static int RETRY_TIMES;
    @NonFinal
    static LockClient LOCK_CLIENT;

    @Override
    public LockStrategy getStrategy() {
        return LockStrategy.DEFAULT;
    }

    @Override
    public Lock lock(String lockKey) {
        return new InnerLock(lockKey);
    }

    @Override
    public void afterPropertiesSet() {
        this.initLock();
        LOCK_CLIENT = SpringHelper.getBean(LockClient.class);
        RETRY_TIMES = SpringHelper.getBean(LockProperties.class).getRetryTime();
    }

    public void initLock() {
        if (LOCKER != null) {
            return;
        }
        synchronized (DefaultLockAction.class) {
            if (LOCKER == null) {
                LOCKER = this;
            }
        }
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class InnerLock implements Lock {

        String lockKey;
        String lockValue;

        @NonFinal
        boolean lockMark = false;
        @NonFinal
        int retryTimes = 0;

        public InnerLock(String lockKey) {
            this.lockKey = lockKey;
            this.lockValue = LOCKER.getHostName() + LOCK_SPLIT + Thread.currentThread().getName() + LOCK_SPLIT + UUID.randomUUID();
        }

        @Override
        public boolean tryLock() {
            String value = LOCK_CLIENT.get(lockKey);
            if (value == null) {
                Long result = LOCK_CLIENT.setnx(lockKey, lockValue, LOCK_TIME_OUT_SECOND);
                if (result != null && result == 1) {
                    return lockMark = true;
                } else if (result != null && retryTimes++ < RETRY_TIMES) {
                    return tryLock();
                }
            } else {
                return StringUtils.equals(lockValue, value);
            }
            return false;
        }

        @Override
        public void unlock() {
            if (lockMark) {
                LOCK_CLIENT.delete(lockKey);
                lockMark = false;
            }
        }
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "[unknown_host]";
        }
    }

}
