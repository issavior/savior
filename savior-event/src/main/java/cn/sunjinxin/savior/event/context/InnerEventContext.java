package cn.sunjinxin.savior.event.context;

import cn.sunjinxin.savior.event.control.Eventer;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author sunjinxin
 * @since 2023/12/28 13:55
 */
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnerEventContext<EventType, RequestParam> {
    EventContext<EventType, RequestParam> eventContext;
    Eventer eventer;
}
