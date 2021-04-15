package com.windhc.brisk.ioc.bean;

/**
 * Bean Define
 *
 * @author windhc
 */
public class BeanDefine {

    private Object bean;
    private Class<?> clazz;

    public BeanDefine(Object bean) {
        this(bean, bean.getClass());
    }

    public BeanDefine(Object bean, Class<?> clazz) {
        this.bean = bean;
        this.clazz = clazz;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
