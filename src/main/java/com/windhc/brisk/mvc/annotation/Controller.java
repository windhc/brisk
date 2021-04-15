package com.windhc.brisk.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    /**
     * path
     */
    String value() default "/";

    /**
     * response json
     */
    boolean json() default true;
}
