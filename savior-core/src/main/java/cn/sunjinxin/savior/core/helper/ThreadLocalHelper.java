package cn.sunjinxin.savior.core.helper;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * super thread local
 *
 * @author sunjinxin
 * @since 2023/12/25 20:11
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThreadLocalHelper {

    static TransmittableThreadLocal<Map<String, Object>> TL = new TransmittableThreadLocal<>();

    public static void put(String key, Object object) {
        Optional.ofNullable(TL.get()).orElseGet(() -> {
            Map<String, Object> map = Maps.newHashMap();
            TL.set(map);
            return map;
        }).put(key, object);
    }

    public static Object get(String key) {
        return Optional.of(TL.get()).map(r -> r.get(key)).orElse(null);
    }

    public static <T> T get(String key, Class<T> clazz) {
        return Optional.of(TL.get()).map(r -> r.get(key)).map(clazz::cast).orElse(null);
    }

    public static void close() {
        TL.remove();
    }

    public static ExecutorService of(ExecutorService threadPoolExecutor) {
        return TtlExecutors.getTtlExecutorService(threadPoolExecutor);
    }

    public static Runnable of(Runnable runnable) {
        return TtlRunnable.get(runnable);
    }

}
