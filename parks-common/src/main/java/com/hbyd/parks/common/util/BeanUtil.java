package com.hbyd.parks.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 封装 Bean 相关的方法
 * <pre>
 * PropertyDescriptor's javadoc:
 * A PropertyDescriptor describes one property that a Java Bean exports via a pair of accessor methods.
 * PropertyDescriptor 用于描述 property, 这个 property 对应一对 accessor (setter and getter)
 * </pre>
 */
public class BeanUtil {
    /**
     * 使用 Java Bean API 反射调用 Getter
     *
     * @param propertyName Getter 对应的属性名称
     * @param obj          Getter 所在的对象
     * @return Getter 的返回值，如果调用失败，返回 Null;
     */
    public static Object invokeGetter(String propertyName, Object obj) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, obj.getClass());
            Method getter = propertyDescriptor.getReadMethod();
//            Class<?> propertyType = propertyDescriptor.getPropertyType();
//            System.out.println(propertyType);
            return getter.invoke(obj, null);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用 Java Bean API 反射调用 Setter
     *
     * @param propertyName Setter 对应的属性名称
     * @param obj          Setter 所在的对象
     * @param arg         Setter 所需参数，setter 只有一个参数
     */
    public static void invokeSetter(String propertyName, Object obj, Object arg) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, obj.getClass());
            Method setter = propertyDescriptor.getWriteMethod();
            setter.invoke(obj, arg);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void invokeSetter_2(String propertyName, Object obj, Object arg) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if(propertyDescriptor.getName().equals(propertyName)){
                    Method setter = propertyDescriptor.getWriteMethod();
                    if(setter != null){
                        setter.invoke(obj, arg);
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
