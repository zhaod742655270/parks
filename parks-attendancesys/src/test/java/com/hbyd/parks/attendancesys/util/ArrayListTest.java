package com.hbyd.parks.attendancesys.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试集合类型的转换
 */
public class ArrayListTest {
    /**
     * How to create ArrayList (ArrayList<T>) from array (T[])
     */
    @Test
    public void testAsList(){
        String[] strArr = {"one", "two", "three"};
        List<String> list = Arrays.asList(strArr);


//      The list returned from asList has fixed size. So, if you want to be able to add
//      or remove elements from the returned list in your code, you'll need to wrap it
//      in a new ArrayList. Otherwise you'll get an UnsupportedOperationException.
//        list.add("four");//UnsupportedOperationException

//      wrap it in a new ArrayList
        ArrayList newList = new ArrayList(list);//本质：转存到另一个数组
        newList.add("four");
        System.out.println(newList);

//      The list returned from asList() is backed by the original array.
//      If you modify the original array, the list will be modified as well.
//      This may be surprising.
        strArr[1] = "five";
        System.out.println(list);//list is backed by strArr, thus changed.
        System.out.println(newList);//newList is backed by new array, thus unchanged.
    }
}
