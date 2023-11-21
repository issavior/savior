package cn.sunjinxin.savior.event.listener.sub;

import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.listener.Listener;
import com.google.common.eventbus.Subscribe;

import java.util.Objects;
import java.util.Optional;

/**
 * Sync Api
 *
 * @author issavior
 */
public interface SyncListener<T, R> extends Listener<T, EventContext<T, R>> {

    @Override
    default boolean enable(T t) {
        return Objects.equals(t, supportEventType());
    }

    @Subscribe
    default void subscribe(EventContext<T, R> context) {
        Optional.ofNullable(context)
                .filter(r -> enable(context.getEventType()))
                .ifPresent(this::handle);
    }

    @Override
    default void afterPropertiesSet() {
        Eventer.SYNC.register(this);
    }
}