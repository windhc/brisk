package com.windhc.brisk.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author windhc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Get {

    /**
     * The path mapping URIs (e.g. {@code "/hello"}).
     * Ant-style path patterns are also supported (e.g. {@code "/hello/**"}).
     */
    String[] value() default {};
}
