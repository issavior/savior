package cn.sunjinxin.savior.event.handler.async.impl;

import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.convert.EventCommon;
import cn.sunjinxin.savior.event.handler.async.AsyncEventHandler;
import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * default async
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultAsyncEventHandler extends AsyncEventHandler {

    static volatile AtomicReference<AsyncEventBus> INSTANCE = new AtomicReference<>();

    @Override
    public List<EventStrategy> strategy() {
        return Lists.newArrayList(EventStrategy.DEFAULT);
    }

    @Override
    public void register(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r -> of().register(r));
    }

    @Override
    @SuppressWarnings("all")
    public void post(Object eventContext) {
        Lists.newArrayList(eventContext).forEach(r -> of().post(InnerEventContext.builder()
                .eventContext(((InnerEventContext) r).getEventContext())
                .eventer(Eventer.ASYNC)
                .build()));
    }

    @Override
    public void unregister(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r -> of().unregister(r));
    }

    private static EventBus of() {
        return Optional.ofNullable(INSTANCE.get()).orElseGet(() ->
                INSTANCE.updateAndGet(r ->
                        new AsyncEventBus(EventCommon.buildThreadPoolTaskExecutor())));
    }
}
