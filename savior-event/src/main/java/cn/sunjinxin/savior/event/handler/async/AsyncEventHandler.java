package cn.sunjinxin.savior.event.handler.async;

import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.control.Eventer;

/**
 * async
 *
 * @author issavior
 */
public abstract class AsyncEventHandler implements EventHandler {

    @Override
    public Eventer event() {
        return Eventer.ASYNC;
    }
}
