package cn.sunjinxin.savior.event.configuration;

import cn.sunjinxin.savior.event.EventRun;
import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.container.EventContainer;
import com.google.common.collect.Sets;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Bean
    public List<EventHandler> eventHandlers() {
        return Optional.of(new Reflections(EventRun.class.getPackage().getName()))
                .map(r -> r.getSubTypesOf(EventHandler.class))
                .orElse(Sets.newHashSet())
                .stream()
                .filter(r -> !r.isInterface())
                .filter(r -> !Modifier.isAbstract(r.getModifiers()))
                .map(EventAutoConfiguration::newInstance)
                .collect(Collectors.toList());
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
