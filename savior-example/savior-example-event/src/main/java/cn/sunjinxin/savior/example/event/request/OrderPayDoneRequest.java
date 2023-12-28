package cn.sunjinxin.savior.example.event.request;

import java.util.Date;

/**
 * @author sunjinxin
 * @since 2023/12/28 13:26
 */
public class OrderPayDoneRequest {

    private Long orderId;
    private Date payTime;
    private Object others;
}
