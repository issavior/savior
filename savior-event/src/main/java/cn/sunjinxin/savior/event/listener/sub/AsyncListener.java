package cn.sunjinxin.savior.event.listener.sub;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.anno.SpringAsyncListener;
import cn.sunjinxin.savior.event.container.EventContainer;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.listener.Listener;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.util.Optional;

/**
 * Async Api
 *
 * @author issavior
 */
@SuppressWarnings("all")
public interface AsyncListener<EventType, RequestParam> extends Listener<EventType, EventContext<EventType, RequestParam>> {

    /**
     * t
     *
     * @param t /
     * @return /
     */
    @Override
    default boolean enable(EventType t) {
        return supportEventType().contains(t);
    }

    @Override
    default void onEvent(InnerEventContext event, long l, boolean b) {
        subscribe(event);
    }

    /**
     * subscribe
     *
     * @param context /
     */
    @Subscribe
    @AllowConcurrentEvents
    @SpringAsyncListener
    default void subscribe(InnerEventContext<EventType, RequestParam> context) {
        Optional.ofNullable(context)
                .filter(r -> enable(context.getEventContext().getEventType()))
                .filter(r -> r.getEventer() == Eventer.ASYNC)
                .ifPresent(r -> this.handle(r.getEventContext()));
    }

    /**
     * register
     */
    @Override
    default void afterPropertiesSet() {
        SpringHelper.getBean(EventContainer.class).of(Eventer.ASYNC).register(this);
    }
}
