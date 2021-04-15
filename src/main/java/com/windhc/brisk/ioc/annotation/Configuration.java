package com.windhc.brisk.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {

    String name() default "";
}
