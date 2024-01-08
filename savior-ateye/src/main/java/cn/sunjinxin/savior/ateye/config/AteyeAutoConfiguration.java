package cn.sunjinxin.savior.ateye.config;

import cn.sunjinxin.savior.ateye.api.AteyeEngine;
import cn.sunjinxin.savior.ateye.api.AteyeMappingApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunjinxin
 * @since 2024/1/8 20:53
 */
@Configuration
public class AteyeAutoConfiguration {

    @Bean
    public AteyeProperties ateyeProperties(){
        return new AteyeProperties();
    }

    @Bean
    public AteyeEngine ateyeEngine(){
        return new AteyeEngine();
    }

    @Bean
    public AteyeMappingApi ateyeMappingApi(){
        return new AteyeMappingApi();
    }


}
