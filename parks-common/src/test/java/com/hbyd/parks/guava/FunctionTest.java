package com.hbyd.parks.guava;

import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import junit.framework.Assert;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 The Function interface provides us with the ability to
 transform objects and the Predicate interface gives us a powerful mechanism for
 filtering. The Functions and Predicates classes also help us write code that is
 easier to maintain and much easier to change. Suppliers help by providing essential
 collaborating objects while completely hiding the details of how those objects are
 created. Combined with a dependency injection framework such as Spring or Guice,
 these interfaces will allow us to seamlessly change the behavior of our programs by
 simply providing a different implementation.
 */
public class FunctionTest {
    /**
     * the Function interface is used to transform objects
     */
    @Test
    public void testFunction(){
        StringLengthFunction lenFunc = new StringLengthFunction();

//      这样使用 Function 意义不大
        Integer apply = lenFunc.apply("hello world");
        System.out.println(apply);

//      使用 Function 处理集合，比较方便，类似于 JS 中的 map()
        Iterable<Integer> transform = Iterables.transform(Lists.newArrayList("hello", "tom"), lenFunc);
        System.out.println(transform);
    }

    /**
     * The Functions class contains a handful of useful methods for working with Function instances.
     */
    @Test
    public void testFunctions(){
//      1. Functions.forMap();
//      Returns a function which performs a map lookup. The returned function throws
//      an IllegalArgumentException if given a key that does not exist in the map.
        Function<String, String> mapFunc = Functions.forMap(Splitter.on("#").withKeyValueSeparator("=").split("1=one#2=two"));
        Assert.assertEquals("one", mapFunc.apply("1"));
        Assert.assertEquals("two", mapFunc.apply("2"));

        try {
            mapFunc.apply("3");
        } catch (Exception e) {
            org.junit.Assert.assertThat(e, CoreMatchers.is(IllegalArgumentException.class));
        }

//      2. Functions.compose();
        StringLengthFunction lenFunc = new StringLengthFunction();
//        Functions.compose(mapFunc, lenFunc);//编译失败，因为泛型不匹配
        Function<String, Integer> compose = Functions.compose(lenFunc, mapFunc);//参数依次为：Function<B,C>, Function<A,B>, 结果通过 B 连接：A,C

//      compose(funcA, funcB) 的执行顺序: funcA.apply(funcB.apply(xxx))
        Integer len = compose.apply("1");
        org.junit.Assert.assertEquals(3, len.intValue());
    }

    /**
     * Predicate: 对泛型的内容做出判断
     * like Function, a Predicate function should not have any side effects
     * Function/Predicate.apply(arg)
     *      arg 在 apply() 执行完毕后不应有变化(side effects)
     */
    @Test
    public void testPredicate(){
        Person jack = new Person();
        jack.setId(1);
        jack.setName("jack");

        Person tom = new Person();
        tom.setId(2);
        tom.setName("tom");

        Book book = new Book();
        book.setPrice(12.23d);
        book.setTitle("Red");
        book.setAuthor(jack);
        book.setPublisher("PacketPub");

        Book book2 = new Book();
        book2.setPrice(133d);
        book2.setTitle("Gone With The Wind");
        book2.setAuthor(tom);
        book2.setPublisher("PacketPub");

        HashMap<String, Book> map = Maps.newHashMap();
        map.put("jack", book);
        map.put("tom", book2);

        Function<String, Book> lookup = Functions.forMap(map);

//      1. 单独判定
        CheapBookPredicate cheapPre = new CheapBookPredicate();
        boolean r1 = cheapPre.apply(book);
        Assert.assertTrue(r1);

//      2. 逻辑判定
//      and:
//        If any of the component Predicate instances return false, the evaluation of
//        any other Predicate instances is stopped.
        JackAuthorPredicate jackPre = new JackAuthorPredicate();
        Predicate<Book> jackCheap = Predicates.and(jackPre, cheapPre);

        boolean r2 = jackCheap.apply(book);
        Assert.assertTrue(r2);

//      or:
//        Once a component Predicate instance returns true, no further evaluations are made.
        Predicate<Book> or = Predicates.or(jackPre, cheapPre);
        boolean r3 = or.apply(book);
        Assert.assertTrue(r3);

//      not:
        boolean r4 = Predicates.not(cheapPre).apply(book2);//不便宜
        Assert.assertTrue(r4);

//      3. 复合判定
        Predicate<String> compose = Predicates.compose(cheapPre, lookup);//参数：Predicate<B>, Function<A,B> 通过 B 连接： Predicate<A>
//      查看 Predicates.compose() 形成的 Predicate 的 apply() 源码：
//        p.apply(f.apply(a));
//        p 表示 Predicate, f 表示 Function

        Assert.assertTrue(compose.apply("jack"));//jack 的书便宜
        Assert.assertTrue(Predicates.not(compose).apply("tom"));//tom 的书不便宜

    }

    /**
     * Supplier 接口：
     * the power of the Supplier interface is that it abstracts the complexity and details of
     * how an object needs to be created, leaving the developer free to create an object in
     * whatever way he/she feels is the best approach.
     *
     * since the Supplier is an interface, unit testing becomes much easier, as compared
     * to other approaches for creating objects such as a static factory method.
     */
    @Test
    public void testSupplier(){
        Person jack = new Person();
        jack.setId(1);
        jack.setName("jack");

        Person tom = new Person();
        tom.setId(2);
        tom.setName("tom");

        Book book = new Book();
        book.setPrice(12.23d);
        book.setTitle("Red");
        book.setAuthor(jack);
        book.setPublisher("PacketPub");

        Book book2 = new Book();
        book2.setPrice(133d);
        book2.setTitle("Gone With The Wind");
        book2.setAuthor(tom);
        book2.setPublisher("PacketPub");

        HashMap<String, Book> map = Maps.newHashMap();
        map.put("jack", book);
        map.put("tom", book2);

        final Function<String, Book> lookup = Functions.forMap(map);
        final Predicate<Book> cheapPre = new CheapBookPredicate();

        Supplier<Predicate<String>> supplier = new Supplier<Predicate<String>>() {
            @Override
            public Predicate<String> get() {
//              here we are choosing to return a new instance each time. We could have just as easily
//              done all of this work in the constructor of this Supplier class
//              and returned the same instance with each call to get, but as we will see next, Guava
//              provides an easier alternative - Suppliers
                return Predicates.compose(cheapPre, lookup);
            }
        };

        Assert.assertTrue(supplier.get().apply("jack"));//jack 的书便宜
        Assert.assertTrue(Predicates.not(supplier.get()).apply("tom"));//tom 的书不便宜

        try {
            supplier.get().apply("unknown");//lookup 报错
        } catch (Exception e) {
            org.junit.Assert.assertThat(e, CoreMatchers.is(IllegalArgumentException.class));
        }

//      每次调用 supplier.get() 都创建了不同的实例
        Predicate<String> pre1 = supplier.get();
        Predicate<String> pre2 = supplier.get();

        Assert.assertNotSame(pre1, pre2);//pre1 != pre2

/*
    The Suppliers' memoize method returns a Supplier instance that wraps a provided
    delegate Supplier instance. When the first call to get is executed, the call is passed
    to the delegate Supplier instance; it creates and returns the instance to the wrapping
    Supplier object. The wrapping Supplier object caches the instance before returning
    it to the caller. All subsequent calls to the get method return the cached instance.

    wrapping Supplier 包裹了 delegate Supplier, 初次调用前者的 get() 时，前者就会调用后者的 get() 并缓存其返回值
*/
//      每次调用 supplier.get() 都使用相同的实例
        Supplier<Predicate<String>> wrapper_1 = Suppliers.memoize(supplier);
        Predicate<String> pre3 = wrapper_1.get();
        Predicate<String> pre4 = wrapper_1.get();

        Assert.assertSame(pre3, pre4);//pre3 == pre4

        Assert.assertTrue(pre3.apply("jack"));//jack 的书便宜
        Assert.assertTrue(Predicates.not(pre3).apply("tom"));//tom 的书不便宜


//      Suppliers.momoizeWithExpiration():
//          Returns a supplier that caches the instance supplied by the delegate and removes
//          the cached value after the specified time has passed. Subsequent calls to get()
//          return the cached value if the expiration time has not passed. After the expiration
//          time, a new value is retrieved, cached, and returned.
        Supplier<Predicate<String>> wrapper_2 = Suppliers.memoizeWithExpiration(supplier, 2l, TimeUnit.MILLISECONDS);
        Predicate<String> pre5 = wrapper_2.get();
        Predicate<String> pre6 = wrapper_2.get();

        Assert.assertSame(pre5, pre6);

        try {
            Thread.sleep(1000);//1000ms
            Predicate<String> pre7 = wrapper_2.get();
            System.out.println(pre5);
            System.out.println(pre7);
            Assert.assertNotSame(pre5, pre7);
            Assert.assertFalse(pre5 == pre7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//      将过期时间的单位修改为：纳秒，重新测试
        Supplier<Predicate<String>> wrapper_3 = Suppliers.memoizeWithExpiration(supplier, 2l, TimeUnit.MILLISECONDS);
        Predicate<String> pre8 = wrapper_3.get();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            if(i == 0 || i == 99999){
                System.out.println(pre8 == wrapper_3.get());//i==0 时，为 true, i==99999 时，为 false
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);//3ms

//      Suppliers.memoizeWithExpiration() 的适用场景：
//        1. delegateSupplier.get() 定时从数据库获取最新值
//        2. Using the Supplier interface with dependency injection is a powerful combination.
//      Guice 中提供了和 Supplier<T> 类似的接口 Provider<T>
    }
}

/**
 * 将 java.util.Date 转换为字符串对应形式
 */
class DateTimeFormatFunction implements Function<Date, String>{
    @Override
    public String apply(@Nullable Date input) {
        return new SimpleDateFormat("yyyy-MM-dd").format(input);
    }
}

/**
 * 获取字符串长度
 */
class StringLengthFunction implements Function<String, Integer>{
//    A good Function implementation should have no side effects, meaning the
//    object passed as an argument should remain unchanged after the apply
//    method has been called.
    @Override
    public Integer apply(@Nullable String input) {
        return input.length();
    }
}

/**
 * 判定：一本书是否便宜
 */
class CheapBookPredicate implements Predicate<Book>{
    @Override
    public boolean apply(@Nullable Book input) {
        return input.getPrice() < 100;//低于 100 元的书才便宜
    }
}

/**
 * 判定：一本书是否由 Jack 所著
 */
class JackAuthorPredicate implements Predicate<Book>{
    @Override
    public boolean apply(@Nullable Book input) {
        return "jack".equals(input.getAuthor().getName());
    }
}
