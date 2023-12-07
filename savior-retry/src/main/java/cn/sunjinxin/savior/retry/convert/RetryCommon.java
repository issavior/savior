package cn.sunjinxin.savior.retry.convert;

import cn.sunjinxin.savior.retry.RetryRun;
import cn.sunjinxin.savior.retry.retryer.RetryAction;
import com.google.common.collect.Sets;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * common
 *
 * @author issavior
 */
public class RetryCommon {

    /**
     * buildLockActions
     *
     * @return /
     */
    public static List<RetryAction> buildRetryers() {
        Reflections reflections = new Reflections(RetryRun.class.getPackage().getName());
        return Optional.ofNullable(reflections.getSubTypesOf(RetryAction.class)).orElse(Sets.newHashSet())
                .stream()
                .filter(r -> !r.isInterface())
                .filter(r -> !Modifier.isAbstract(r.getModifiers()))
                .map(RetryCommon::newInstance)
                .collect(Collectors.toList());
    }

    /**
     * newInstance
     *
     * @param r /
     * @return /
     */
    private static RetryAction newInstance(Class<? extends RetryAction> r) {
        try {
            return r.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
