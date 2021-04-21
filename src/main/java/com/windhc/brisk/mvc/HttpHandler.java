package com.windhc.brisk.mvc;

import com.windhc.brisk.mvc.http.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.http.server.HttpRequest;
import org.smartboot.http.server.HttpResponse;
import org.smartboot.http.server.HttpServerHandle;

import java.io.IOException;

/**
 * @author windhc
 */
public class HttpHandler extends HttpServerHandle {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    @Override
    public void doHandle(HttpRequest request, HttpResponse response) throws IOException {
        HttpContext context = new HttpContext(request, response);

    }
}
