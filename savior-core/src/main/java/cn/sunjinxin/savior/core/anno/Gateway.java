package cn.sunjinxin.savior.core.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author issavior
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Gateway {

}
