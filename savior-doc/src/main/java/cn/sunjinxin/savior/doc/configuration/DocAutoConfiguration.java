package cn.sunjinxin.savior.doc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * EventAutoConfiguration
 *
 * @author issavior
 */
@Configuration
@EnableAsync
public class DocAutoConfiguration {

    @Bean
    public DocProperties eventProperties() {
        return new DocProperties();
    }

    @Bean
    public DocParser taskExecutor1() {
        return new DocParser();
    }

}
