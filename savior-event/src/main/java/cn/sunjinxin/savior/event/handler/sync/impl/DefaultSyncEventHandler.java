package cn.sunjinxin.savior.event.handler.sync.impl;

import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.handler.sync.SyncEventHandler;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * default sync
 *
 * @author issavior
 */
public class DefaultSyncEventHandler extends SyncEventHandler {

    @Override
    public List<EventStrategy> strategy() {
        return Lists.newArrayList(EventStrategy.DEFAULT,EventStrategy.DISRUPTOR);
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
                .eventer(Eventer.SYNC)
                .build()));
    }

    @Override
    public void unregister(Object eventClass) {
        Lists.newArrayList(eventClass).forEach(r -> of().unregister(r));
    }

    private static EventBus of() {
        return EventBusCenter.INSTANCE;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    private static class EventBusCenter {
        static EventBus INSTANCE = new EventBus();
    }
}
