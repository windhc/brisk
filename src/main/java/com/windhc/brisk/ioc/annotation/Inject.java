package com.windhc.brisk.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {

    String name() default "";

}
