package cn.sunjinxin.savior.event.configuration;

import cn.sunjinxin.savior.event.constant.EventConstant;
import cn.sunjinxin.savior.event.convert.EventCommon;
import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.container.EventContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.List;

/**
 * EventAutoConfiguration
 *
 * @author issavior
 */
@Configuration
public class EventAutoConfiguration {

    @Bean
    public EventProperties eventProperties() {
        return new EventProperties();
    }

    @Bean(EventConstant.EVENT_TASK_EXECUTOR)
    public TaskExecutor taskExecutor1() {
        return EventCommon.buildThreadPoolTaskExecutor();
    }

    @Bean
    public List<EventHandler> eventHandlers() {
        return EventCommon.buildEventHandlers();
    }

    @Bean
    public EventContainer eventContainer(EventProperties eventProperties, List<EventHandler> eventHandlers) {
        return new EventContainer(eventHandlers, eventProperties);
    }

    private static EventHandler newInstance(Class<? extends EventHandler> r) {
        try {
            return r.newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
