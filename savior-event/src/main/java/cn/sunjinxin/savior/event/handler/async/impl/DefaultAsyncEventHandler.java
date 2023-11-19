package cn.sunjinxin.savior.event.handler.async.impl;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.configuration.EventProperties;
import cn.sunjinxin.savior.event.configuration.ThreadPoolProperties;
import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.handler.async.AsyncEventHandler;
import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * default async
 *
 * @author issavior
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultAsyncEventHandler extends AsyncEventHandler {

    @NonFinal
    static volatile AtomicReference<AsyncEventBus> INSTANCE = new AtomicReference<>();

    @Override
    public EventStrategy strategy() {
        return EventStrategy.DEFAULT;
    }

    @Override
    public void register(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r -> of().register(r));
    }

    @Override
    public void post(Object eventContext) {
        Lists.newArrayList(eventContext).forEach(r -> of().post(r));
    }

    @Override
    public void unregister(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r -> of().unregister(r));
    }

    private static EventBus of() {
        return Optional.ofNullable(INSTANCE.get()).orElseGet(() -> INSTANCE.updateAndGet(r -> new AsyncEventBus(getExecutor())));
    }

    private static ThreadPoolTaskExecutor getExecutor() {
        ThreadPoolProperties pool = SpringHelper.getBean(EventProperties.class).getAsyncThreadPool();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(pool.getCorePoolSize());
        executor.setMaxPoolSize(pool.getMaxPoolSize());
        executor.setQueueCapacity(pool.getQueueCapacity());
        executor.setThreadNamePrefix(pool.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
