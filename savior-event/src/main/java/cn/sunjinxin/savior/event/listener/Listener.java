package cn.sunjinxin.savior.event.listener;

import cn.sunjinxin.savior.core.anno.External;
import cn.sunjinxin.savior.event.context.EventContext;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author issavior
 */
@External
public interface Listener<T, R> extends InitializingBean, EventHandler<EventContext> {

    List<T> supportEventType();

    boolean enable(T t);

    void handle(R r);

}
