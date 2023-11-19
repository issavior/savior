package cn.sunjinxin.savior.event.listener;

import cn.sunjinxin.savior.core.anno.External;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author issavior
 */
@External
public interface Listener<T,R> extends InitializingBean {

    T supportEventType();

    boolean enable(T t);

    void handle(R r);
}
