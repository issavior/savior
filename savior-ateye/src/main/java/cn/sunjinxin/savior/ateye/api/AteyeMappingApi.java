package cn.sunjinxin.savior.ateye.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author sunjinxin
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AteyeMappingApi implements InitializingBean {

    @Resource
    @NonFinal
    RequestMappingHandlerMapping handlerMapping;

    RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

    /**
     * core configuration
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

    public void registerMapping(String path, String requestMethod, Object handler, Method method) {
        RequestMappingInfo mappingInfo = RequestMappingInfo.paths(path)
                .methods(RequestMethod.valueOf(requestMethod))
                .options(this.config)
                .build();
        handlerMapping.registerMapping(mappingInfo, handler, method);
    }

}
