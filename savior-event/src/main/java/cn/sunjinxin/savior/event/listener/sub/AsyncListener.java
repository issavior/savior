package cn.sunjinxin.savior.event.listener.sub;

import cn.sunjinxin.savior.event.anno.SpringAsyncListener;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.listener.Listener;
import cn.sunjinxin.savior.event.control.Eventer;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.util.Objects;
import java.util.Optional;

/**
 * Async Api
 *
 * @author issavior
 */
public interface AsyncListener<T, R> extends Listener<T, EventContext<T, R>> {

    /**
     * t
     *
     * @param t /
     * @return /
     */
    @Override
    default boolean enable(T t) {
        return supportEventType().contains(t);
    }

    /**
     * subscribe
     *
     * @param context /
     */
    @Subscribe
    @AllowConcurrentEvents
    @SpringAsyncListener
    default void subscribe(EventContext<T, R> context) {
        Optional.ofNullable(context)
                .filter(r -> enable(context.getEventType()))
                .filter(r -> r.getEventer() == Eventer.ASYNC)
                .ifPresent(this::handle);
    }

    /**
     * register
     */
    @Override
    default void afterPropertiesSet() {
        Eventer.ASYNC.register(this);
    }
}
