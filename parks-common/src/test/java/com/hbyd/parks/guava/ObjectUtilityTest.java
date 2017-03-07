package com.hbyd.parks.guava;

import com.google.common.base.Objects;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试 Guava 提供的 Objects 中的方法
 */
public class ObjectUtilityTest {
    /**
     * java.lang.Object.toString()
     * 对应的：
     * Objects.ToStringHelper()
     */
    @Test
    public void testToStringHelper(){
        Person author = new Person();
        author.setId(1);
        author.setName("jack");

        Book book = new Book();

        book.setAuthor(author);
        book.setPrice(12.22d);
        book.setTitle("百年孤独");
        book.setPublisher("机械工业出版社");

        System.out.println(book);

        book.setAuthor(null);

        System.out.println(book);
    }

    /**
     * java.lang.Object.hashCode()
     * 对应的
     * Objects.hashCode(Object...)
     */
    @Test
    public void testHashCode(){
        Person author = new Person();
        author.setId(1);
        author.setName("jack");

        Book book = new Book();

        book.setAuthor(author);
        book.setPrice(12.22d);
        book.setTitle("百年孤独");
        book.setPublisher("机械工业出版社");

        System.out.println(book.hashCode());
    }

    @Test
    public void testComparisonChain(){
        Book b1 = new Book();
        b1.setTitle("a");
        b1.setPrice(1);

        Book b2 = new Book();
        b2.setTitle("a");
        b2.setPrice(2);

        Assert.assertTrue(b1.compareTo(b2) < 0);
    }

    /**
     * 测试 Objects.firstNonNull();
     * 常用来提供默认值
     */
    @Test
    public void testFirstNonNull(){
//        The firstNonNull method can be used as a way of providing a default value when
//        you are not sure if an object is null. A word of caution though: if both arguments are
//        null, a NullPointerException error will be thrown.
        String variable = null;

        String defaultVal = Objects.firstNonNull(variable, "default val");
        System.out.println(defaultVal);

        variable = "hello world";
        String s = Objects.firstNonNull(variable, "default val");
        System.out.println(s);

//        如果两个参数都是 null, 异常：NullPointerException
        try {
            Objects.firstNonNull(null, null);
        } catch (Exception e) {
            Assert.assertThat(e, CoreMatchers.is(NullPointerException.class));
        }
    }
}
