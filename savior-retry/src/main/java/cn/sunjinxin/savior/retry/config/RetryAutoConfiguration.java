package cn.sunjinxin.savior.retry.config;

import cn.sunjinxin.savior.retry.convert.RetryCommon;
import cn.sunjinxin.savior.retry.Retryer;
import cn.sunjinxin.savior.retry.factory.RetryFactory;
import cn.sunjinxin.savior.retry.retryer.RetryAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author sunjinxin
 * @since 2023/11/14 21:47
 */
@Configuration
public class RetryAutoConfiguration {

    @Bean
    public RetryProperties retryProperties() {
        return new RetryProperties();
    }

    @Bean
    public List<RetryAction> retryers() {
        return RetryCommon.buildRetryers();
    }

    @Bean
    public RetryFactory retryFactory() {
        return new RetryFactory(retryProperties(), retryers());
    }

    @Bean
    public Retryer retrySavior() {
        return new Retryer(retryFactory());
    }

}
