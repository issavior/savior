package cn.sunjinxin.savior.core.anno;

import cn.sunjinxin.savior.core.configuration.SaviorAutoconfigurationImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Automatically scan relevant instances of each component through this annotation.
 *
 * @author issavior
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SaviorAutoconfigurationImportSelector.class)
public @interface Savior {

    /**
     * Start or not
     *
     * @return /
     */
    boolean enable() default true;

    /**
     * The scanned package path defaults to the path where the current annotation is located
     *
     * @return /
     */
    String[] value() default {};
}
