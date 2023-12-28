package cn.sunjinxin.savior.example.event;

import cn.sunjinxin.savior.core.anno.Savior;
import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.control.Eventer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;

/**
 * @author sunjinxin
 * @since 2023/12/28 13:18
 */
@SpringBootApplication
@Savior
@Slf4j
public class EventMain {

    public static void main(String[] args) {
        SpringApplication.run(EventMain.class, args);

        log.info("用户已支付订单");
//        Eventer.SYNC.publish(EventContext.builder()
//                        .eventType("ORDER_PAY_DONE_EVENT_VALUE")
//                .build());
        Eventer.ASYNC.publish(EventContext.builder()
                        .eventType("ORDER_PAY_DONE_EVENT_VALUE")
                .build());
        Eventer.ASYNC.publish(EventContext.builder()
                        .eventType("ORDER_PAY_DONE_EVENT_VALUE")
                .build());

    }
}
