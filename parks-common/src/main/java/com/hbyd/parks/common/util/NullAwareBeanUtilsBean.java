package com.hbyd.parks.common.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * 使用 apache commons bean utils 实现的 Bean 拷贝工具
 */
public class NullAwareBeanUtilsBean extends BeanUtilsBean {
    /**
     * 重写单个字段的拷贝规则<br/>
     * Then you can just instantiate a NullAwareBeanUtilsBean and use it to copy <br/>
     * your beans, for example:
     * <pre>{@code
     * BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
     * notNull.copyProperties(dest, orig);
     * }</pre>
     */
    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        System.out.println("当前拷贝的属性是："+ name +", 值为：##" + value + "##");
        if (value == null) {
            //source bean 的属性值为 null, 不拷贝
        } else if (value instanceof String && "".equals(value)) {
            //source bean 的属性值为 empty string, 不拷贝
        } else {
            super.copyProperty(bean, name, value);
        }
    }

    /**
     * 将源对象的所有属性拷贝到目标对象，这个方法是照搬过来的，没有修改，只是为了添加中文注释
     *
     * @param dest 目标对象
     * @param orig 源对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        super.copyProperties(dest, orig);
    }
}
