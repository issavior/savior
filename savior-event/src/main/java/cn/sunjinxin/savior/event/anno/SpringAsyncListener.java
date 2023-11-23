package cn.sunjinxin.savior.event.anno;

import cn.sunjinxin.savior.event.constant.EventConstant;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.*;

/**
 * spring async listener
 *
 * @author issavior
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EventListener
@Async(EventConstant.EVENT_TASK_EXECUTOR)
public @interface SpringAsyncListener {
}
