package cn.sunjinxin.savior.lock.convert;

import cn.sunjinxin.savior.lock.LockRun;
import cn.sunjinxin.savior.lock.config.LockProperties;
import cn.sunjinxin.savior.lock.exception.LockException;
import cn.sunjinxin.savior.lock.locker.LockAction;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import redis.clients.jedis.ConnectionPoolConfig;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * common
 *
 * @author issavior
 */
public class LockCommon {

    /**
     * buildLockActions
     *
     * @return /
     */
    public static List<LockAction> buildLockActions() {
        Reflections reflections = new Reflections(LockRun.class.getPackage().getName());
        return Optional.ofNullable(reflections.getSubTypesOf(LockAction.class)).orElse(Sets.newHashSet())
                .stream()
                .filter(r -> !r.isInterface())
                .filter(r -> !Modifier.isAbstract(r.getModifiers()))
                .map(LockCommon::newInstance)
                .collect(Collectors.toList());
    }

    /**
     * newInstance
     *
     * @param r /
     * @return /
     */
    private static LockAction newInstance(Class<? extends LockAction> r) {
        try {
            return r.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * buildConnectionPoolConfig
     *
     * @return /
     */
    public static ConnectionPoolConfig buildConnectionPoolConfig() {
        ConnectionPoolConfig config = new ConnectionPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(200);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        return config;
    }

    /**
     * check
     *
     * @param lockProperties /
     */
    public static void checkLockFactory(LockProperties lockProperties) {
        Optional.of(lockProperties).filter(r -> StringUtils.isNotEmpty(r.getHost())).orElseThrow(() -> new LockException("savior lock host is null"));
    }
}
