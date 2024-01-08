package cn.sunjinxin.savior.ateye.anno;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author sunjinxin
 * @since 2023/12/29 18:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@Component
public @interface AteyeService {

    String desc() default "";

    boolean enable() default true;
}
