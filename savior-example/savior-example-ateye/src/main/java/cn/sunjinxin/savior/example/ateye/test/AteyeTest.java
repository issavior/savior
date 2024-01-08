package cn.sunjinxin.savior.example.ateye.test;

import cn.sunjinxin.savior.ateye.anno.AteyeInvoker;
import cn.sunjinxin.savior.ateye.anno.AteyeService;
import cn.sunjinxin.savior.ateye.res.AteyeRes;

/**
 * @author sunjinxin
 * @since 2024/1/8 21:08
 */
@AteyeService
public class AteyeTest {

    @AteyeInvoker
    public AteyeRes test() {
        return AteyeRes.of("测试成功");
    }
}
