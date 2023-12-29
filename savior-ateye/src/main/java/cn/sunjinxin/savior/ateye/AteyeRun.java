package cn.sunjinxin.savior.ateye;

import cn.sunjinxin.savior.ateye.api.TestRequestMappingService;
import cn.sunjinxin.savior.core.anno.Savior;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author sunjinxin
 * @since 2023/12/29 14:38
 */
@SpringBootApplication
@Savior
@Component
public class AteyeRun {

    public static void main(String[] args) throws NoSuchMethodException {
        ConfigurableApplicationContext run = SpringApplication.run(AteyeRun.class, args);
        TestRequestMappingService bean = run.getBean(TestRequestMappingService.class);
        bean.registerMapping("apis","GET");
//        RequestMappingApi.register();
    }
}
