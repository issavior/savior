package cn.sunjinxin.savior.event.handler.async;

import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.control.EventBus;

/**
 * async
 *
 * @author issavior
 */
public abstract class AsyncEventHandler implements EventHandler {

    @Override
    public EventBus event() {
        return EventBus.ASYNC;
    }
}
