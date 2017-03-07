package com.hbyd.parks.attendancesys.util;

import com.hbyd.parks.common.util.ReflectionUtil;
import com.hbyd.parks.domain.attendancesys.ShiftAssign;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射测试，因为排班算法中用到了反射，因为表结构所致
 */
public class ReflectionUtilTest {
    @Test
    public void testShiftAssign(){
        ShiftAssign sa = new ShiftAssign();

        for (int i = 1; i <= 31; i++) {
            String setterName = "setS" + i;
            try {
                sa.getClass().getMethod(setterName, String.class).invoke(sa, "shift_id_" + i);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        for (int i = 1; i <= 31; i++){
            String getterName = "getS" + i;
            try {
                Object shiftFK = sa.getClass().getMethod(getterName).invoke(sa);
                System.out.println(shiftFK);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReflectionUtil(){
        ReflectionUtil.printGettersSetters(ShiftAssign.class);
    }

    @Test
    public void testGetAccessors(){
        List<Method> accessors = ReflectionUtil.getAccessors(ShiftAssign.class);
        for (Method accessor : accessors) {
            System.out.println(accessor.getName());
        }
    }
}
