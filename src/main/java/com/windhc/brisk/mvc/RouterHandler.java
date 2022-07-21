package com.windhc.brisk.mvc;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.windhc.brisk.Brisk;
import com.windhc.brisk.exception.BriskException;
import com.windhc.brisk.ioc.bean.BeanDefine;
import com.windhc.brisk.mvc.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.http.common.utils.AntPathMatcher;
import org.smartboot.http.server.HttpRequest;
import org.smartboot.http.server.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author windhc
 */
public class RouterHandler {
    private static final Logger log = LoggerFactory.getLogger(RouterHandler.class);

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public void route(HttpRequest request, HttpResponse response) {
        for (BeanDefine beanDefine : Brisk.BEAN_FACTORY.getBeanDefines()) {
            // 路由
            Class<?> clazz = beanDefine.getClazz();
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller == null) {
                continue;
            }
            for (Method method : clazz.getMethods()) {
                switch (request.getMethod()) {
                    case "GET" -> {
                        Get get = method.getAnnotation(Get.class);
                        if (get == null) {
                            continue;
                        }
                        for (String item : get.value()) {
                            String path = controller.value() + item;
                            handle(request, response, path, beanDefine, method);
                        }
                    }
                    case "POST" -> {
                        Post post = method.getAnnotation(Post.class);
                        if (post == null) {
                            continue;
                        }
                        for (String item : post.value()) {
                            String path = controller.value() + item;
                            handle(request, response, path, beanDefine, method);
                        }
                    }
                    case "PUT" -> {
                        Put put = method.getAnnotation(Put.class);
                        if (put == null) {
                            continue;
                        }
                        for (String item : put.value()) {
                            String path = controller.value() + item;
                            handle(request, response, path, beanDefine, method);
                        }
                    }
                    case "DELETE" -> {
                        Delete delete = method.getAnnotation(Delete.class);
                        if (delete == null) {
                            continue;
                        }
                        for (String item : delete.value()) {
                            String path = controller.value() + item;
                            handle(request, response, path, beanDefine, method);
                        }
                    }
                    default -> log.warn("cannot match router");
                }
            }
        }
    }

    private void handle(HttpRequest request, HttpResponse response, String pathPattern, BeanDefine beanDefine, Method method) {
        if (antPathMatcher.match(pathPattern, request.getRequestURI())) {
            try {
                List<Object> args = new ArrayList<>();
                Parameter[] parameterList = method.getParameters();
                for (Parameter parameter : parameterList) {
                    if (parameter.getAnnotations().length == 0) {
                        args.add(null);
                        continue;
                    }
                    handleQueryParam(request, parameter, args);

                    handleRequestBody(request, parameter, args);
                }

                Object result = method.invoke(beanDefine.getBean(), args.toArray());
                if (result == null) {
                    response.write("".getBytes(StandardCharsets.UTF_8));
                    return;
                }
                if (result instanceof String str) {
                    response.write(str.getBytes(StandardCharsets.UTF_8));
                } else {
                    response.write(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
                }
            } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                throw new BriskException(e);
            }
        }
    }

    private void handleQueryParam(HttpRequest request, Parameter parameter, List<Object> args) {
        QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
        if (queryParam == null) {
            return;
        }
        String name = queryParam.name();
        if (StrUtil.isBlank(name)) {
            log.warn("parameter name is blank");
            return;
        }
        String value = request.getParameter(name);
        if (value == null && StrUtil.isNotEmpty(queryParam.defaultValue())) {
            args.add(queryParam.defaultValue());
        } else {
            args.add(value);
        }
    }

    private void handlePathParam(HttpRequest request, Parameter parameter, List<Object> args) {
        PathParam pathParam = parameter.getAnnotation(PathParam.class);
        if (pathParam == null) {
            return;
        }
        String name = pathParam.name();
        if (StrUtil.isBlank(name)) {
            log.warn("parameter name is blank");
            return;
        }
        String value = request.getParameter(name);
        if (value == null && StrUtil.isNotEmpty(pathParam.defaultValue())) {
            args.add(pathParam.defaultValue());
        } else {
            args.add(value);
        }
    }

    private void handleRequestBody(HttpRequest request, Parameter parameter, List<Object> args) {
        RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
        if (requestBody == null) {
            return;
        }
        try {
            InputStreamReader in = new InputStreamReader(request.getInputStream());
            String body = new BufferedReader(in).lines().collect(Collectors.joining(System.lineSeparator()));
            args.add(JSON.parseObject(body, parameter.getType()));
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
