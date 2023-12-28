package cn.sunjinxin.savior.event.control;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.container.EventContainer;
import cn.sunjinxin.savior.event.context.InnerEventContext;

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
        InnerEventContext innerEventContext = new InnerEventContext();
        innerEventContext.setEventContext(context);
        innerEventContext.setEventer(this);
        SpringHelper.getBean(EventContainer.class).of(this).post(innerEventContext);
    }
}
