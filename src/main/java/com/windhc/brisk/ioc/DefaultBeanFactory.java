package com.windhc.brisk.ioc;

import com.windhc.brisk.ioc.bean.BeanDefine;

import java.util.List;
import java.util.Set;

public class DefaultBeanFactory implements BeanFactory {

    @Override
    public void addBean(Object bean) {

    }

    @Override
    public void addBean(String name, Object bean) {

    }

    @Override
    public <T> T addBean(Class<T> type) {
        return null;
    }

    @Override
    public Object createBean(Class<?> type) {
        return null;
    }

    @Override
    public void setBean(Class<?> type, Object proxyBean) {

    }

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return null;
    }

    @Override
    public List<BeanDefine> getBeanDefines() {
        return null;
    }

    @Override
    public BeanDefine getBeanDefine(Class<?> type) {
        return null;
    }

    @Override
    public List<Object> getBeans() {
        return null;
    }

    @Override
    public Set<String> getBeanNames() {
        return null;
    }

    @Override
    public void remove(Class<?> type) {

    }

    @Override
    public void remove(String beanName) {

    }

    @Override
    public void clearAll() {

    }
}
