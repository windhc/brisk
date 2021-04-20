package com.windhc.brisk.mvc.http;

import org.smartboot.http.server.HttpRequest;
import org.smartboot.http.server.HttpResponse;

/**
 * @author windhc
 */
public class HttpContext {

    private final HttpRequest request;
    private final HttpResponse response;

    public HttpContext(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpRequest request() {
        return this.request;
    }

    public HttpResponse response() {
        return this.response;
    }
}
