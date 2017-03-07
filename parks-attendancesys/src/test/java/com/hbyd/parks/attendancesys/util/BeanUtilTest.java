package com.hbyd.parks.attendancesys.util;

import com.hbyd.parks.common.util.BeanUtil;
import com.hbyd.parks.domain.attendancesys.ShiftAssign;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;

import java.beans.Expression;
import java.beans.PropertyDescriptor;
import java.beans.Statement;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by allbutone on 2014/10/24.
 */
public class BeanUtilTest {

    /**
     * 测试：java.beans.Statement，不推荐, 效率明显低于反射, 这里只做简单测试
     *
     * 继承关系如下：
     * <pre>
     * java.beans.Statement
     *          |---java.beans.Expression
     * </pre>
     * @see <a href="http://ainthek.blogspot.com/2010/08/raw-reflection-vs-javabeansexpression.html">Raw reflection vs java.beans.Expression 性能测试</a>
     */
    @Test
    public void testStatement(){
        ShiftAssign sa = new ShiftAssign();
        Statement statement = new Statement(sa, "setS1", new Object[]{"hello"});
        try {
            System.out.println(statement.getMethodName());//setS1
            System.out.println(statement.getArguments()[0]);//hello

//            System.out.println(statement.getTarget());//NullPointerException

            statement.execute();
            System.out.println(sa.getS1());//hello
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试：java.beans.Expression，不推荐, 效率明显低于反射, 这里只做简单测试
     * @see <a href="http://ainthek.blogspot.com/2010/08/raw-reflection-vs-javabeansexpression.html">Raw reflection vs java.beans.Expression 性能测试</a>
     */
    @Test
    public void testExpression(){
        ShiftAssign sa = new ShiftAssign();
        try {
            String val = (String) new Expression(sa, "setS1", new Object[]{"hello"}).getValue();
            System.out.println(val);//setter 没有返回值

            String r = (String) new Expression(sa, "getS1", new Object[]{}).getValue();
            System.out.println(r);//hello
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 java bean api
     */
    @Test
    public void testInvokeSetter(){
        ShiftAssign sa = null;
        try {
            sa = ShiftAssign.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtil.invokeSetter("s1", sa, "100");
        Object val = BeanUtil.invokeGetter("s1", sa);

        System.out.println(val);
    }

    /**
     * 测试 java bean api
     */
    @Test
    public void testInvokeSetter_2(){
        ShiftAssign sa = null;
        try {
            sa = ShiftAssign.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtil.invokeSetter_2("s1", sa, "100");
        Object val = BeanUtil.invokeGetter("s1", sa);

        System.out.println(val);
    }

    /**
     * 使用 Apache BeanUtils 完成 Getter 的反射调用
     */
    @Test
    public void testInvokeGetterWithBeanUtils(){
        ShiftAssign sa = new ShiftAssign();
        try {
            BeanUtils.setProperty(sa, "s1", "haha");
            String s1 = BeanUtils.getProperty(sa, "s1");
            System.out.println(s1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * If you happen to use spring framework, you could use the PropertyAccessorFactory for retrieving an implementation of the PropertyAccessor interface
     */
    @Test
    public void testInvokeGetterWithSpring(){
        ShiftAssign sa = new ShiftAssign();

//      Accessing properties directly
        ConfigurablePropertyAccessor propAccessor = PropertyAccessorFactory.forDirectFieldAccess(sa);

        propAccessor.setPropertyValue("s1", "hello");
        Object s1 = propAccessor.getPropertyValue("s1");
        System.out.println(s1);//hello

        Class type = propAccessor.getPropertyType("s1");
        System.out.println(type.getName());//java.lang.String

        TypeDescriptor typeDescriptor = propAccessor.getPropertyTypeDescriptor("s1");
        System.out.println(typeDescriptor.getName());//java.lang.String

//      Accessing properties through accessors/mutators
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(sa);

        beanWrapper.setPropertyValue("s1", "world");
        Object val = beanWrapper.getPropertyValue("s1");
        System.out.println(val);//world

        Class type2 = beanWrapper.getPropertyType("s1");
        System.out.println(type2.getName());//java.lang.String

        TypeDescriptor typeDescriptor1 = beanWrapper.getPropertyTypeDescriptor("s1");
        System.out.println(typeDescriptor1.getName());//java.lang.String

        PropertyDescriptor propertyDescriptor = beanWrapper.getPropertyDescriptor("s1");
        System.out.println(propertyDescriptor.getName());//s1
    }
}
