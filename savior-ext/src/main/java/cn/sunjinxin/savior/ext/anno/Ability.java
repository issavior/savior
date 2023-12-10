package cn.sunjinxin.savior.ext.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author issavior
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Ability {

    /**
     * industry
     *
     * @return /
     */
    String industry() default "default";

    /**
     * business
     *
     * @return B2C、B2B、B2S
     */
    String business() default "default";

}
