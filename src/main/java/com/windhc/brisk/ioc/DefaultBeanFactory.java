package com.windhc.brisk.ioc;

import com.windhc.brisk.ioc.bean.BeanDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author windhc
 */
public class DefaultBeanFactory implements BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private final Map<String, BeanDefine> pool = new ConcurrentHashMap<>(32);

    @Override
    public void addBean(Object bean) {
        addBean(bean.getClass().getName(), bean);
    }

    @Override
    public void addBean(String name, Object bean) {
        BeanDefine beanDefine = new BeanDefine(bean);
        put(name, beanDefine);
        Class<?>[] interfaces = beanDefine.getClazz().getInterfaces();
        if (interfaces.length > 0) {
            for (Class<?> interfaceClazz : interfaces) {
                this.put(interfaceClazz.getName(), beanDefine);
            }
        }
    }

    @Override
    public Object getBean(String name) {
        BeanDefine beanDefine = pool.get(name);
        if (beanDefine == null) {
            return null;
        }
        return beanDefine.getBean();
    }

    @Override
    public List<BeanDefine> getBeanDefines() {
        return new ArrayList<>(pool.values());
    }

    @Override
    public BeanDefine getBeanDefine(String name) {
        return pool.get(name);
    }

    @Override
    public List<Object> getBeans() {
        List<Object> beans = new ArrayList<>(pool.keySet().size());
        for (Map.Entry<String, BeanDefine> entry : pool.entrySet()) {
            beans.add(entry.getValue().getBean());
        }
        return beans;
    }

    @Override
    public Set<String> getBeanNames() {
        return pool.keySet();
    }

    @Override
    public void remove(String beanName) {
        pool.remove(beanName);
    }

    @Override
    public void clearAll() {
        pool.clear();
    }

    private void put(String name, BeanDefine beanDefine) {
        if (pool.put(name, beanDefine) != null) {
            logger.warn("Duplicated Bean: {}", name);
        }
    }
}
