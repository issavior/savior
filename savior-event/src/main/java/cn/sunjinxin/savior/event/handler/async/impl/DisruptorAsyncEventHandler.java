package cn.sunjinxin.savior.event.handler.async.impl;

import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.convert.EventCommon;
import cn.sunjinxin.savior.event.handler.async.AsyncEventHandler;
import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * default async
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DisruptorAsyncEventHandler extends AsyncEventHandler {

    /**
     * 环形缓冲区大小
     */
    private static final int BUFFER_SIZE = 16;
    static volatile AtomicReference<Disruptor<Event>> INSTANCE = new AtomicReference<>();

    @Override
    public EventStrategy strategy() {
        return EventStrategy.DISRUPTOR;
    }

    @Override
    public void register(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r ->
                of().handleEventsWith((EventHandler) r)
        );
    }

    @Override
    public void post(Object eventContext) {
        Lists.newArrayList(eventContext).forEach(r ->
                of().getRingBuffer()
                        .publishEvent((event, l) -> event.setEventContext((EventContext) r))
        );
    }

    @Override
    public void unregister(Object eventClass) {

    }

    private static Disruptor<Event> of() {
        return Optional.ofNullable(INSTANCE.get()).orElseGet(() -> {

            Disruptor<Event> eventDisruptor = INSTANCE.updateAndGet(r ->
                    new Disruptor<>(Event::new,
                            BUFFER_SIZE,
                            new CustomizableThreadFactory("event-handler-")));
            eventDisruptor.start();
            return eventDisruptor;

        });
    }

    static class Event {

        @Setter
        @Getter
        private EventContext eventContext;
    }
}
