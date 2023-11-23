package cn.sunjinxin.savior.event.handler.async.impl;

import cn.sunjinxin.savior.core.helper.SpringHelper;
import cn.sunjinxin.savior.event.constant.EventStrategy;
import cn.sunjinxin.savior.event.handler.async.AsyncEventHandler;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * spring sync
 *
 * @author issavior
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpringAsyncEventHandler extends AsyncEventHandler {

    @Override
    public EventStrategy strategy() {
        return EventStrategy.SPRING;
    }

    @Override
    public void register(Object eventClass) {
        // ignore
    }

    @Override
    public void post(Object eventContext) {
        Lists.newArrayList(eventContext).forEach(SpringHelper::publish);
    }

    @Override
    public void unregister(Object eventClass) {
        // ignore
    }
}
