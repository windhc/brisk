package com.windhc.brisk.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HeaderParam {

    String name() default "";

    String defaultValue() default "";
}
