package cn.sunjinxin.savior.ateye.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Service
@Slf4j
public class TestRequestMappingService implements InitializingBean {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

    /**
     * 核心配置类，因为`RequestMappingHandlerMapping`中config属性未提供公共方法，所以需要自行构建
     */
    @Override
    public void afterPropertiesSet() {
        this.config.setTrailingSlashMatch(handlerMapping.useTrailingSlashMatch());
        this.config.setContentNegotiationManager(handlerMapping.getContentNegotiationManager());

        if (handlerMapping.getPatternParser() != null) {
            this.config.setPatternParser(handlerMapping.getPatternParser());
        } else {
            this.config.setPathMatcher(handlerMapping.getPathMatcher());
        }
    }


    public ResponseEntity<String> execute() throws Throwable {
        System.out.println("abcd");

        //todo 业务执行逻辑
        return ResponseEntity.ok("hello");
    }

    /**
     * 注册
     * @param pattern
     * @param method
     */
    public void registerMapping(String pattern,String method) throws NoSuchMethodException {
        RequestMappingInfo mappingInfo = RequestMappingInfo.paths(pattern)
                .methods(RequestMethod.valueOf(method))
                .options(this.config)
                .build();
        Method targetMethod = TestRequestMappingService.class.getDeclaredMethod("execute");
        handlerMapping.registerMapping(mappingInfo, this, targetMethod);
    }
}
