package cn.sunjinxin.savior.event.control;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.container.EventContainer;
import cn.sunjinxin.savior.event.listener.Listener;

/**
 * Eventer
 *
 * @author issavior
 */
public enum Eventer {

    /**
     * SYNC
     */
    SYNC,

    /**
     * ASYNC
     */
    ASYNC;

    public <T, R> void publish(EventContext<T, R> context) {
        context.setEventer(this);
        SpringHelper.getBean(EventContainer.class).of(this).post(context);
    }

    public <T, R> void register(Listener<T, R> listener) {
        SpringHelper.getBean(EventContainer.class).of(this).register(listener);
    }
}
