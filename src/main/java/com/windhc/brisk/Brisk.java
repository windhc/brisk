package com.windhc.brisk;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.windhc.brisk.ioc.BeanFactory;
import com.windhc.brisk.ioc.DefaultBeanFactory;
import com.windhc.brisk.ioc.annotation.Bean;
import com.windhc.brisk.ioc.annotation.Configuration;
import com.windhc.brisk.ioc.annotation.Inject;
import com.windhc.brisk.ioc.bean.BeanDefine;
import com.windhc.brisk.mvc.RouterHandler;
import com.windhc.brisk.mvc.annotation.Controller;
import com.windhc.brisk.scheduling.ScheduleService;
import com.windhc.brisk.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.http.server.HttpBootstrap;
import org.smartboot.http.server.handle.HttpRouteHandle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author windhc
 */
public class Brisk {
    private static final Logger logger = LoggerFactory.getLogger(Brisk.class);

    public Brisk() {
    }

    public Brisk start(int port, String basePackage) {
        logger.info("brisk start");
        BeanFactory beanFactory = new DefaultBeanFactory();
        HttpRouteHandle routeHandle = new HttpRouteHandle();

        Set<Class<?>> classes = ClassUtil.scanPackage(basePackage);
        //注册bean
        for (Class<?> clazz : classes) {
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller != null) {
                beanFactory.addBean(ReflectUtil.newInstance(clazz));
                continue;
            }
            Bean bean = clazz.getAnnotation(Bean.class);
            if (bean != null) {
                if (StrUtil.isBlank(bean.value())) {
                    beanFactory.addBean(ReflectUtil.newInstance(clazz));
                } else {
                    beanFactory.addBean(bean.value(), ReflectUtil.newInstance(clazz));
                }
                continue;
            }
            Configuration configuration = clazz.getAnnotation(Configuration.class);
            if (configuration != null) {
                Object object = ReflectUtil.newInstance(clazz);
                for (Method method : clazz.getMethods()) {
                    Bean mBean = method.getAnnotation(Bean.class);
                    if (mBean == null) {
                        continue;
                    }
                    Object result = ReflectUtil.invoke(object, method);
                    if (StrUtil.isBlank(mBean.value())) {
                        beanFactory.addBean(result);
                    } else {
                        beanFactory.addBean(mBean.value(), result);
                    }
                }
            }
        }
        // 依赖注入
        for (BeanDefine beanDefine : beanFactory.getBeanDefines()) {
            Field[] fields = beanDefine.getClazz().getDeclaredFields();
            for (Field field : fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject == null) {
                    continue;
                }
                field.setAccessible(true);
                Object bean;
                if (StrUtil.isBlank(inject.name())) {
                    bean = beanFactory.getBean(field.getType().getName());
                } else {
                    bean = beanFactory.getBean(inject.name());
                }
                ReflectUtil.setFieldValue(beanDefine.getBean(), field, bean);
            }
        }

        RouterHandler routerHandler = new RouterHandler();
        for (BeanDefine beanDefine : beanFactory.getBeanDefines()) {
            // 注册路由
            Class<?> clazz = beanDefine.getClazz();
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller != null) {
                for (Method method : clazz.getMethods()) {
                    routerHandler.route(beanDefine.getBean(), method, routeHandle, controller.value());
                }
            }

            // 添加定时任务
            for (Method method : clazz.getMethods()) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);
                if (scheduled != null) {
                    ScheduleService.instance().addSchedule(scheduled.cron(), beanDefine.getBean(), method);
                }
            }
        }
        logger.debug("bean: {}", beanFactory.getBeans());

        HttpBootstrap bootstrap = new HttpBootstrap();
        bootstrap.pipeline().next(routeHandle);
        bootstrap.setPort(port).start();
        return this;
    }
}
