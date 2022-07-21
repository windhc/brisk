package com.windhc.brisk.mvc;

import com.windhc.brisk.mvc.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.http.server.HttpRequest;
import org.smartboot.http.server.HttpResponse;
import org.smartboot.http.server.HttpServerHandler;

import java.io.IOException;

/**
 * @author windhc
 */
public class HttpHandler extends HttpServerHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);

    private final RouterHandler routerHandler = new RouterHandler();

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        HttpContext context = new HttpContext(request, response);
        ContextHolder.set(context);

        routerHandler.route(request, response);

        ContextHolder.remove();
    }

}
