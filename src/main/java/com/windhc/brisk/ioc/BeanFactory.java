package com.windhc.brisk.ioc;

import com.windhc.brisk.ioc.bean.BeanDefine;

import java.util.List;
import java.util.Set;

/**
 * bean factory interface
 *
 * @author windhc
 */
public interface BeanFactory {

    /**
     * add bean to ioc container
     *
     * @param bean bean instance
     */
    void addBean(Object bean);

    /**
     * add bean to ioc container
     *
     * @param name bean name
     * @param bean bean instance
     */
    void addBean(String name, Object bean);

    /**
     * get bean instance by name
     *
     * @param name bean name
     * @return return bean instance
     */
    Object getBean(String name);

    /**
     * get all BeanDefine
     *
     * @return List<BeanDefine>
     */
    List<BeanDefine> getBeanDefines();

    /**
     * get BeanDefine by bean name
     *
     * @param name bean name
     * @return return BeanDefine instance
     */
    BeanDefine getBeanDefine(String name);

    /**
     * get all beans
     *
     * @return return bean list
     */
    List<Object> getBeans();

    /**
     * get all bean names
     *
     * @return return bean name set
     */
    Set<String> getBeanNames();

    /**
     * remove bean by name
     *
     * @param beanName bean name
     */
    void remove(String beanName);

    /**
     * clean all bean
     */
    void clearAll();
}
