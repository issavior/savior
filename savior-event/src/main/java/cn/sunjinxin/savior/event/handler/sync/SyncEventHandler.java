package cn.sunjinxin.savior.event.handler.sync;

import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.control.Eventer;

/**
 * sync
 *
 * @author issavior
 */
public abstract class SyncEventHandler implements EventHandler {

    @Override
    public Eventer event() {
        return Eventer.SYNC;
    }
}
