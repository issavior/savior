package cn.sunjinxin.savior.event.handler.async.impl;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.configuration.EventProperties;
import cn.sunjinxin.savior.event.configuration.ThreadPoolProperties;
import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.handler.async.AsyncEventHandler;
import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * default async
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings("all")
public class DisruptorAsyncEventHandler extends AsyncEventHandler {

    private static final int BUFFER_SIZE = 1024;
    static volatile AtomicReference<Disruptor<InnerEventContext>> INSTANCE = new AtomicReference<>();
    static volatile AtomicBoolean startMark = new AtomicBoolean(false);

    @Override
    public List<EventStrategy> strategy() {
        return Lists.newArrayList(EventStrategy.DISRUPTOR);
    }

    @Override
    public void register(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r ->
                of().handleEventsWith((EventHandler) r)
        );
    }

    @Override
    public void post(Object eventContext) {
        Optional.of(startMark.get()).filter(BooleanUtils::isFalse).ifPresent(r -> {
            of().start();
            startMark.set(true);
        });
        Lists.newArrayList(eventContext).forEach(r ->
                of().getRingBuffer()
                        .publishEvent((event, l) -> {
                            event.setEventContext(((InnerEventContext) r).getEventContext());
                            event.setEventer(Eventer.ASYNC);
                        })
        );
    }

    @Override
    public void unregister(Object eventClass) {

    }

    private static Disruptor<InnerEventContext> of() {
        return Optional.ofNullable(INSTANCE.get()).orElseGet(() ->
                INSTANCE.updateAndGet(r ->
                        new Disruptor<>(InnerEventContext::new,
                                BUFFER_SIZE,
                                new CustomizableThreadFactory(SpringHelper.getBean(EventProperties.class).getAsyncThreadPool().getThreadNamePrefix()))));
    }
}
