package cn.sunjinxin.savior.event.listener.sub;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.container.EventContainer;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.listener.Listener;
import com.google.common.eventbus.Subscribe;
import org.springframework.context.event.EventListener;

import java.util.Optional;

/**
 * Sync Api
 *
 * @author issavior
 */
@SuppressWarnings("all")
public interface SyncListener<EventType, RequestParam> extends Listener<EventType, EventContext<EventType, RequestParam>> {

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
    @EventListener
    default void subscribe(InnerEventContext<EventType, RequestParam> context) {
        Optional.ofNullable(context)
                .filter(r -> enable(context.getEventContext().getEventType()))
                .filter(r -> r.getEventer() == Eventer.SYNC)
                .ifPresent(r -> this.handle(r.getEventContext()));
    }

    /**
     * register
     */
    @Override
    default void afterPropertiesSet() {
        SpringHelper.getBean(EventContainer.class).of(Eventer.SYNC).register(this);
    }
}
