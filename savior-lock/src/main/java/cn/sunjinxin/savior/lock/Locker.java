package cn.sunjinxin.savior.lock;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.lock.factory.LockFactory;
import cn.sunjinxin.savior.lock.locker.Lock;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * lock api
 *
 * @author sunjinxin
 * @since 2023/11/15 14:32
 */
public class Locker {

    /**
     * get Lock instance
     *
     * @param key /
     * @return Lock instance
     */
    public static Lock of(String key) {
        LockFactory bean = SpringHelper.getBean(LockFactory.class);
        return bean.build().lock(key);
    }

    /**
     * try lock
     *
     * @param key               /
     * @param exceptionSupplier get Lock fail throw exception
     * @param <T>               Throwable
     * @return Lock instance
     * @throws T Throwable
     */
    public static <T extends Throwable> Lock tryLock(String key, Supplier<T> exceptionSupplier) throws T {
        Lock lock = SpringHelper.getBean(LockFactory.class).build().lock(key);
        Optional.of(lock.tryLock()).filter(BooleanUtils::isTrue).orElseThrow(exceptionSupplier);
        return lock;
    }

}
