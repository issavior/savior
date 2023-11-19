package cn.sunjinxin.savior.core.anno;

import java.lang.annotation.*;

/**
 * Declare interfaces that require external implementation to instantiate related instances.
 *
 * @author issavior
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface External {
}
