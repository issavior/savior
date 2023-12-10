package cn.sunjinxin.savior.ext.configuration;

import cn.sunjinxin.savior.ext.container.ExtContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * auto
 * @author issavior
 */
@Configuration
public class ExtAutoConfiguration {

    @Bean
    public ExtContainer extContainer() {
        return new ExtContainer();
    }
}
