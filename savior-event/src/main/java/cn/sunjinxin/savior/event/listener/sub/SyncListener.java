package cn.sunjinxin.savior.event.listener.sub;

import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.listener.Listener;
import com.google.common.eventbus.Subscribe;
import org.springframework.context.event.EventListener;

import java.util.Objects;
import java.util.Optional;

/**
 * Sync Api
 *
 * @author issavior
 */
public interface SyncListener<T, R> extends Listener<T, EventContext<T, R>> {

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
    @EventListener
    default void subscribe(EventContext<T, R> context) {
        Optional.ofNullable(context)
                .filter(r -> enable(context.getEventType()))
                .filter(r -> r.getEventer() == Eventer.SYNC)
                .ifPresent(this::handle);
    }

    /**
     * register
     */
    @Override
    default void afterPropertiesSet() {
        Eventer.SYNC.register(this);
    }
}
