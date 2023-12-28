package cn.sunjinxin.savior.example.event.listener;

import cn.sunjinxin.savior.event.context.EventContext;
import cn.sunjinxin.savior.event.listener.sub.AsyncListener;
import cn.sunjinxin.savior.example.event.request.OrderPayDoneRequest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author sunjinxin
 * @since 2023/12/28 13:23
 */
@Slf4j
public class OrderPayDoneEventAsyncListener implements AsyncListener<String, OrderPayDoneRequest>{

    private static final String ORDER_PAY_DONE_EVENT_KEY = "ORDER_PAY_DONE_EVENT_VALUE";

    @Override
    public List<String> supportEventType() {
        return Lists.newArrayList(ORDER_PAY_DONE_EVENT_KEY);
    }


    @Override
    public void handle(EventContext<String, OrderPayDoneRequest> stringOrderPayDoneRequestEventContext) {


        log.info("支付完成异步事件开始处理处理...");

        log.info("开始发货...");

    }
}
