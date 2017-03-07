package com.hbyd.parks.common.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;

import static com.hbyd.parks.common.util.SqlHelper.getIntCandidates;
import static com.hbyd.parks.common.util.SqlHelper.getStringCandidadtes;

public class SqlHelperTest {

    @Test
    public void testGetStringCandidadtes() throws Exception {
        ArrayList<String> items = Lists.newArrayList("hello", "world");
        System.out.println(getStringCandidadtes(items));//('hello','world')

        ArrayList<String> itemsWithNull = Lists.newArrayList("hello", null, "world");
        System.out.println(getStringCandidadtes(itemsWithNull));//('hello','world')
    }

    @Test
    public void testGetIntCandidates() throws Exception {
        ArrayList<Integer> items = Lists.newArrayList(1, 2);
        System.out.println(getIntCandidates(items));//(1,2)

        ArrayList<Integer> itemsWithNull = Lists.newArrayList(1, null, 2);
        System.out.println(getIntCandidates(itemsWithNull));//(1,2)
    }
}