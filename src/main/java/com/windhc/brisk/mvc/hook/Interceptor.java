package com.windhc.brisk.mvc.hook;

import com.windhc.brisk.mvc.http.HttpContext;

/**
 * @author windhc
 */
public interface Interceptor {

    boolean before(HttpContext httpContext);

    boolean after(HttpContext httpContext);
}
