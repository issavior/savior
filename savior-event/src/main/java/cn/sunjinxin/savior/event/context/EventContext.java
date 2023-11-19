package cn.sunjinxin.savior.event.context;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class EventContext<T, R> {

    T eventType;

    R request;

    String eventId;
}
