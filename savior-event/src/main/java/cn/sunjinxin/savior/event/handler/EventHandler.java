package cn.sunjinxin.savior.event.handler;

import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.control.Eventer;

/**
 * event handler
 * <p>This interface provides policy distribution, event registration, event distribution, and event removal.</p>
 *
 * @author issavior
 */
public interface EventHandler {

    EventStrategy strategy();

    Eventer event();

    void register(Object eventListener);

    void post(Object eventContext);

    void unregister(Object eventClass);
}
