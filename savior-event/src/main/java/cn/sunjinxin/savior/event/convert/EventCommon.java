package cn.sunjinxin.savior.event.convert;

import cn.hutool.core.lang.Assert;
import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.EventRun;
import cn.sunjinxin.savior.event.configuration.EventProperties;
import cn.sunjinxin.savior.event.configuration.ThreadPoolProperties;
import cn.sunjinxin.savior.event.exception.EventException;
import cn.sunjinxin.savior.event.handler.EventHandler;
import com.google.common.collect.Sets;
import org.reflections.Reflections;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * common
 *
 * @author issavior
 */
public class EventCommon {

    /**
     * Properties to ThreadPoolTaskExecutor
     *
     * @return /
     */
    public static ThreadPoolTaskExecutor buildThreadPoolTaskExecutor() {
        ThreadPoolProperties properties = SpringHelper.getBean(EventProperties.class).getAsyncThreadPool();
        checkThreadPoolProperties(properties);
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(properties.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(properties.getMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(properties.getQueueCapacity());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setThreadNamePrefix(properties.getThreadNamePrefix());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(5);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    /**
     * check
     *
     * @param properties /
     */
    private static void checkThreadPoolProperties(ThreadPoolProperties properties) {
        Assert.isFalse(properties.getCorePoolSize() > properties.getMaxPoolSize()
                , () -> new EventException("savior-event`s ThreadPoolProperties has exception:[CorePoolSize > MaxPoolSize]"));
    }

    /**
     * build
     *
     * @return /
     */
    public static List<EventHandler> buildEventHandlers() {
        return Optional.of(new Reflections(EventRun.class.getPackage().getName()))
                .map(r -> r.getSubTypesOf(EventHandler.class))
                .orElse(Sets.newHashSet())
                .stream()
                .filter(r -> !r.isInterface())
                .filter(r -> !Modifier.isAbstract(r.getModifiers()))
                .map(EventCommon::newInstance)
                .collect(Collectors.toList());
    }

    /**
     * instance
     *
     * @param r /
     * @return /
     */
    private static EventHandler newInstance(Class<? extends EventHandler> r) {
        try {
            return r.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
