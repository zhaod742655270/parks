package com.hbyd.parks.common.util;

import com.hbyd.parks.common.cxf.FaultBuilder;
import com.hbyd.parks.common.model.AjaxMessage;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.log4j.Logger;

import javax.xml.ws.soap.SOAPFaultException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装反射相关的方法
 */
public class ReflectionUtil {

    private static Logger logger = Logger.getLogger(ReflectionUtil.class);

    /*
    * getMethods():
    * Returns an array containing Method objects reflecting all the public member methods
    * of the class or interface represented by this Class object, including those declared
    * by the class or interface and those inherited from superclasses and superinterfaces.
    *
    * 返回 public 修饰的方法，包括继承而来的方法
    *
    * getDeclaredMethods():
    * Returns an array of Method objects reflecting all the methods declared by the class
    * or interface represented by this Class object. This includes public, protected,
    * default (package) access, and private methods, but excludes inherited methods.
    *
    * 返回 private, protected, default 修饰的方法，但不包括继承的方法
    * */


    /**
     * 打印类中的 setter 和 getter, 包含继承而来的方法 <br/>
     * Getters and Setters are also known as Accessor and Mutator methods. <br/>
     *
     * @param aClass 目标类
     * @see <a href="http://tutorials.jenkov.com/java-reflection/getters-setters.html">Java Reflection - Getters and Setters</a>
     */
    public static void printGettersSetters(Class aClass) {
        Method[] methods = aClass.getMethods();//getMethods() 包含继承来的方法

        for (Method method : methods) {
            if (isGetter(method)) System.out.println("getter: " + method);
            if (isSetter(method)) System.out.println("setter: " + method);
        }
    }

    /**
     * 获取类自身声明的 setter 和 getter 构成的列表
     *
     * @param clazz 目标类
     * @return 方法列表
     */
    public static List<Method> getAccessors(Class clazz) {
        List<Method> list = new ArrayList<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            if (isGetter(declaredMethod) || isSetter(declaredMethod)) {
                list.add(declaredMethod);
            }
        }
        return list;
    }

    /**
     * First of all, getter methods must be public.
     * A getter method have its name start with "get" or "is", take 0 parameters, and
     * returns a value.
     *
     * @param method 方法实例
     * @return 如果方法是 Getter, 返回 true, 否则 false;
     * @see <a href="http://tutorials.jenkov.com/java-reflection/getters-setters.html">Java Reflection - Getters and Setters</a>
     */
    public static boolean isGetter(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) return false;//Public 修饰
        if (!(method.getName().startsWith("get") || method.getName().startsWith("is"))) return false;//get 开头
        if (method.getParameterTypes().length != 0) return false;//没有参数
        if (void.class.equals(method.getReturnType())) return false;//有返回值, isXXX 返回的必须是 boolean 这点没有判断
        return true;
    }

    /**
     * @param method
     * @return
     */
    public static boolean isGetter_2(Method method) {
        if (Modifier.isPublic(method.getModifiers()) &&
                method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*") &&
                    !method.getReturnType().equals(void.class))
                return true;
            if (method.getName().matches("^is[A-Z].*") &&
                    method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }

    /**
     * @param method
     * @return
     */
    public static boolean isSetter_2(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                method.getReturnType().equals(void.class) &&//setter 难道不能有返回值？
                method.getParameterTypes().length == 1 &&
                method.getName().matches("^set[A-Z].*");
    }

    /**
     * First of all, setter methods must be public.
     * A setter method have its name start with "set", and takes 1 parameter.
     * <p/>
     * 下面的描述是 java bean 规范？
     * Setters may or may not return a value. Some setters return void, some
     * the value set, others the object the setter were called on for use in
     * method chaining. Therefore you should make no assumptions about the
     * return type of a setter.
     *
     * @param method 方法实例
     * @return 如果是 Setter, 返回 true, 否则 false;
     */
    public static boolean isSetter(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) return false;//Public 修饰
        if (!method.getName().startsWith("set")) return false;//set 开头
        if (method.getParameterTypes().length != 1) return false;//1个参数
        return true;
    }

    /**
     * 反射调用实例方法
     *
     * @param instance   实例
     * @param methodName 方法名称
     * @param paramTypes 方法参数类型数组
     * @param args       方法的调用参数
     */
    public static void invoke(Object instance, String methodName, Class[] paramTypes, Object[] args) {
        try {
            Method method = instance.getClass().getDeclaredMethod(methodName, paramTypes);
            method.invoke(instance, args);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {//目标方法(服务方法调用)执行出现异常
            throw (SOAPFaultException) e.getTargetException();
        }
    }

    /**
     * 调用服务方法(适用于无返回值的服务方法)
     *
     * @return {@link com.hbyd.parks.common.model.AjaxMessage}
     */
    public static AjaxMessage invokeWsMethod(Object instance, String methodName, Class[] paramTypes, Object[] args) {
        AjaxMessage message = new AjaxMessage();
        message.setSuccess(true);//默认是成功的

        String errorMsg = "服务异常，操作终止";
        try {
            invoke(instance, methodName, paramTypes, args);
        } catch (SOAPFaultException e) {//可预见的异常
            SoapFault fault = (SoapFault) e.getCause();
            message.setSuccess(false);
            if (FaultBuilder.NOT_ALLOWED == fault.getStatusCode()) {
                message.setMessage(fault.getReason());
            } else {
                logger.error(errorMsg, e);
                message.setMessage(errorMsg);
            }
        } catch (RuntimeException e) {//不可预见的异常
            logger.error(errorMsg, e);
            message.setSuccess(false);
            message.setMessage(errorMsg);
        }
        return message;
    }

    /**
     * 判断对象是否有某个字段
     *
     * @param obj       对象
     * @param fieldName 字段名称
     * @return 如果有，返回 true, 否则 false
     */
    public static boolean hasField(Object obj, String fieldName) {
        try {
            obj.getClass().getField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
