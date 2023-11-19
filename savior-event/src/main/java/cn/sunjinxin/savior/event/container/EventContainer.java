package cn.sunjinxin.savior.event.container;

import cn.sunjinxin.savior.event.configuration.EventProperties;
import cn.sunjinxin.savior.event.handler.EventHandler;
import cn.sunjinxin.savior.event.control.EventBus;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

/**
 * EventContainer
 *
 * @author issavior
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventContainer {

    List<EventHandler> eventHandlers;

    EventProperties eventProperties;

    public EventHandler of(EventBus event) {
        return Optional.ofNullable(eventHandlers).orElse(Lists.newArrayList())
                .stream()
                .filter(r -> r.strategy() == eventProperties.getStrategy())
                .filter(r -> r.event() == event)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("can`t match event handler"));
    }
}
