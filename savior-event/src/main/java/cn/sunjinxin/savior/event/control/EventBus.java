package cn.sunjinxin.savior.event.control;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.container.EventContainer;

/**
 * bus
 *
 * @author issavior
 */

public enum EventBus {

    /**
     * SYNC
     */
    SYNC,

    /**
     * ASYNC
     */
    ASYNC;

    public <T, R> void publish(EventContext<T, R> context) {
        SpringHelper.getBean(EventContainer.class).of(this).post(context);
    }

    public void register(Object listener) {
        SpringHelper.getBean(EventContainer.class).of(this).register(listener);
    }
}
