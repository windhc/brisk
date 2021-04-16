package com.windhc.brisk.mvc;

import com.windhc.brisk.exception.BriskException;
import com.windhc.brisk.mvc.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.http.server.HttpRequest;
import org.smartboot.http.server.HttpResponse;
import org.smartboot.http.server.HttpServerHandle;
import org.smartboot.http.server.handle.HttpRouteHandle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author windhc
 */
public class RouterHandler {
    private static final Logger logger = LoggerFactory.getLogger(RouterHandler.class);

    public void route(final Object object, final Method method, HttpRouteHandle routeHandle, String basePath) {
        get(object, method, routeHandle, basePath);
    }

    private void get(final Object object, final Method method, HttpRouteHandle routeHandle, String basePath) {
        Get get = method.getAnnotation(Get.class);
        if (get == null) {
            return;
        }
        for (String path : get.value()) {
            logger.info("add route {}", basePath + path);
            routeHandle.route(basePath + path, new HttpServerHandle() {
                @Override
                public void doHandle(HttpRequest request, HttpResponse response) throws IOException {
                    try {
                        String invoke = (String) method.invoke(object);
                        response.write(invoke.getBytes());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new BriskException(e);
                    }
                }
            });
        }
    }
}
