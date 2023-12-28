package cn.sunjinxin.savior.event.listener;

import cn.sunjinxin.savior.core.anno.External;
import cn.sunjinxin.savior.event.context.InnerEventContext;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author issavior
 */
@External
public interface Listener<EventType, EventContext> extends InitializingBean, EventHandler<InnerEventContext> {

    List<EventType> supportEventType();

    boolean enable(EventType type);

    void handle(EventContext context);

}
