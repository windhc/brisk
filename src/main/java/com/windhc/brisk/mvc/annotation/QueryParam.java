package com.windhc.brisk.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryParam {

    String name();

    String defaultValue() default "";
}
