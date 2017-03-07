package com.hbyd.parks.attendancesys.util;

import com.hbyd.parks.common.util.NullAwareBeanUtilsBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * 注意：测试时不要使用内部类测试，因为：
 * copyProperties(dest, orig) 在调用 copyProperty(bean, name, value) 前会判断
 * 源对象的属性是否可读可写：
 *      getPropertyUtils().isReadable(orig, name)//如果 orig 为内部类的实例，返回 false, 不可读
 *      getPropertyUtils().isWritable(orig, name)//如果 orig 为内部类的实例，返回 false, 不可写
 *
 * 由于内部类实例的 property 不可读，不可写，因此属性拷贝没有执行(ignored), 测试不通过
 *
 *
 */
public class NullAwareBeanUtilsBeanTest {

    private NullAwareBeanUtilsBean notNullUtil;

    @Before
    public void init(){
        notNullUtil = new NullAwareBeanUtilsBean();
    }

    @Test
    public void testCopyProperties() throws Exception {
        TestBean orig = new TestBean();
        orig.setId(1);
        orig.setName("");
        orig.setDate(null);

        TestBean dest = new TestBean();
        dest.setId(2);
        dest.setName("destName");
        dest.setDate(new Date());

        System.out.println(notNullUtil.getPropertyUtils().isReadable(orig, "id"));
        System.out.println(notNullUtil.getPropertyUtils().isWriteable(orig, "id"));
        System.out.println(notNullUtil.getPropertyUtils().isReadable(orig, "name"));
        System.out.println(notNullUtil.getPropertyUtils().isWriteable(orig, "name"));
        System.out.println(notNullUtil.getPropertyUtils().isReadable(orig, "date"));
        System.out.println(notNullUtil.getPropertyUtils().isWriteable(orig, "date"));

        notNullUtil.copyProperties(dest, orig);

        Assert.assertEquals(1, dest.getId());//id 被更新
        Assert.assertEquals("destName", dest.getName());//如果源字段为空字符串，不更新
        Assert.assertNotNull(dest.getDate());//如果源字段为 null, 不更新

        System.out.println("orig:" + orig.toString());//{id=1, date=null, name=''}
        System.out.println("dest:" + dest.toString());//{id=1, date=Wed Aug 06 11:14:06 CST 2014, name='destName'}
    }
}
