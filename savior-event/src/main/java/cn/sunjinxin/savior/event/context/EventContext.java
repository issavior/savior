package cn.sunjinxin.savior.event.context;

import cn.sunjinxin.savior.event.control.Eventer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * context
 *
 * @author issavior
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventContext<T, R> implements Serializable {

    T eventType;

    R request;

    String eventId;

    Eventer eventer;
}
