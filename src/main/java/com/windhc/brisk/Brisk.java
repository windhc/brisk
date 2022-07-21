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
import com.windhc.brisk.mvc.HttpHandler;
import com.windhc.brisk.mvc.annotation.Controller;
import com.windhc.brisk.scheduling.ScheduleService;
import com.windhc.brisk.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.http.server.HttpBootstrap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author windhc
 */
public class Brisk {
    private static final Logger log = LoggerFactory.getLogger(Brisk.class);

    public static final BeanFactory BEAN_FACTORY = new DefaultBeanFactory();

    private HttpBootstrap bootstrap;

    public Brisk() {
    }

    public Brisk start(int port, String basePackage) {
        log.info("brisk start");
        Set<Class<?>> classes = ClassUtil.scanPackage(basePackage);
        //注册bean
        for (Class<?> clazz : classes) {
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller != null) {
                BEAN_FACTORY.addBean(ReflectUtil.newInstance(clazz));
                continue;
            }
            Bean bean = clazz.getAnnotation(Bean.class);
            if (bean != null) {
                if (StrUtil.isBlank(bean.value())) {
                    BEAN_FACTORY.addBean(ReflectUtil.newInstance(clazz));
                } else {
                    BEAN_FACTORY.addBean(bean.value(), ReflectUtil.newInstance(clazz));
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
                        BEAN_FACTORY.addBean(result);
                    } else {
                        BEAN_FACTORY.addBean(mBean.value(), result);
                    }
                }
            }
        }
        // 依赖注入
        for (BeanDefine beanDefine : BEAN_FACTORY.getBeanDefines()) {
            Field[] fields = beanDefine.getClazz().getDeclaredFields();
            for (Field field : fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject == null) {
                    continue;
                }
                field.setAccessible(true);
                Object bean;
                if (StrUtil.isBlank(inject.name())) {
                    bean = BEAN_FACTORY.getBean(field.getType().getName());
                } else {
                    bean = BEAN_FACTORY.getBean(inject.name());
                }
                ReflectUtil.setFieldValue(beanDefine.getBean(), field, bean);
            }
        }

        for (BeanDefine beanDefine : BEAN_FACTORY.getBeanDefines()) {
            Class<?> clazz = beanDefine.getClazz();
            // 添加定时任务
            for (Method method : clazz.getMethods()) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);
                if (scheduled != null) {
                    ScheduleService.instance().addSchedule(scheduled.cron(), beanDefine.getBean(), method);
                }
            }
        }
        log.debug("bean: {}", BEAN_FACTORY.getBeans());

        bootstrap = new HttpBootstrap();
        bootstrap.httpHandler(new HttpHandler());
        bootstrap.configuration().bannerEnabled(false);
        bootstrap.setPort(port).start();
        //优雅关闭
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
        return this;
    }

    public void stop() {
        bootstrap.shutdown();
        log.info("brisk stopped");
    }
}
