package com.windhc.brisk.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {

    int value() default Integer.MAX_VALUE;
}
