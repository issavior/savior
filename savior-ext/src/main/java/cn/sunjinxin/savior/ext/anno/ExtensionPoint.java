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
public @interface ExtensionPoint {

    /**
     * biz
     *
     * @return ticker、hotel
     */
    String bizCode();

    /**
     * scenario
     *
     * @return /
     */
    String scenario() default "default";

}
