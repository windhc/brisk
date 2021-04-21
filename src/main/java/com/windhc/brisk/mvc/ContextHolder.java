package com.windhc.brisk.mvc;

import com.windhc.brisk.mvc.http.HttpContext;

/**
 * @author windhc
 */
public class ContextHolder {

    private static final ThreadLocal<HttpContext> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前线程的上下文
     */
    public static void set(HttpContext context) {
        THREAD_LOCAL.set(context);
    }

    /**
     * 移除当前线程的上下文
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 获取当前线程的上下文
     */
    public static HttpContext get() {
        return THREAD_LOCAL.get();
    }
}
