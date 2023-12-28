package cn.sunjinxin.savior.event.context;

import cn.sunjinxin.savior.event.control.Eventer;
import lombok.Data;

/**
 * @author sunjinxin
 * @since 2023/12/28 13:55
 */
//@AllArgsConstructor(staticName = "of")
    @Data
public class InnerEventContext<EventType, RequestParam> {

    private EventContext<EventType, RequestParam> eventContext;

   private Eventer eventer;
}
