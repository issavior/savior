package cn.sunjinxin.savior.event.handler.async.impl;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import cn.sunjinxin.savior.event.handler.async.AsyncEventHandler;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * spring sync
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpringAsyncEventHandler extends AsyncEventHandler {

    @Override
    public List<EventStrategy> strategy() {
        return Lists.newArrayList(EventStrategy.SPRING);
    }

    @Override
    public void register(Object eventClass) {
        // ignore
    }

    @Override
    @SuppressWarnings("all")
    public void post(Object eventContext) {
        Lists.newArrayList(eventContext).forEach(r -> SpringHelper.publish(InnerEventContext.builder()
                .eventContext(((InnerEventContext) r).getEventContext())
                .eventer(Eventer.ASYNC)
                .build()));
    }

    @Override
    public void unregister(Object eventClass) {
        // ignore
    }
}
