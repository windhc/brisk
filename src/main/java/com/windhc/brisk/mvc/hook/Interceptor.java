package com.windhc.brisk.mvc.hook;

import com.windhc.brisk.mvc.http.HttpContext;

/**
 * @author windhc
 */
@FunctionalInterface
public interface Interceptor {

    boolean before(HttpContext httpContext);

    default void after(HttpContext httpContext) {
    }
}
