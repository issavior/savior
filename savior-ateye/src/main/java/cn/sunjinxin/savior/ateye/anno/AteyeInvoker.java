package cn.sunjinxin.savior.ateye.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunjinxin
 * @since 2023/12/29 16:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AteyeInvoker {

    String desc() default "";

    boolean enable() default true;
}
