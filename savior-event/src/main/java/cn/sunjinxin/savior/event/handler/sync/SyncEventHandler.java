package cn.sunjinxin.savior.event.handler.sync;

import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.control.EventBus;

/**
 * sync
 *
 * @author issavior
 */
public abstract class SyncEventHandler implements EventHandler {

    @Override
    public EventBus event() {
        return EventBus.SYNC;
    }
}
