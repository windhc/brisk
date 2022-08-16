package com.windhc.brisk;

import com.windhc.brisk.mvc.hook.Interceptor;
import com.windhc.brisk.mvc.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(TestInterceptor.class);

    @Override
    public boolean before(HttpContext httpContext) {
        log.info("before {}", httpContext.request().getRequestURI());
        return true;
    }

    @Override
    public boolean after(HttpContext httpContext) {
        log.info("after {}", httpContext.request().getRequestURI());
        return true;
    }
}
