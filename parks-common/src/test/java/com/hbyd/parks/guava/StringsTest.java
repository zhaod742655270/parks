package com.hbyd.parks.guava;

import com.google.common.base.*;
import com.google.common.collect.Maps;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 测试：Joiner and Splitter
 */
public class StringsTest {

    @Test
    public void testJoiners(){
//      1. 使用 Guava 实现
        String join = Joiner.on("-").skipNulls().join("one", null, "three", "four", null);
        System.out.println(join);

        String join2 = Joiner.on("-").useForNull("empty").join("one", null, "three", "four", null);
        System.out.println(join2);

        Object[] arr = new Object[]{1,2,3,4};
        String join3 = Joiner.on("-").join(arr);
        System.out.println(join3);

        List<Integer> parts = Arrays.asList(1, 2, 3, 4);
        String join4 = Joiner.on("-").join(parts);
        System.out.println(join4);

//      2. 使用 JDK 来实现
        StringBuilder sb = new StringBuilder();
        String delimiter = "-";
        for (Integer part : parts) {
            if(part != null){
                sb.append(part).append(delimiter);
            }
        }

//      The sequence is changed to a new character sequence whose length is specified by the argument.
        sb.setLength(sb.length()-delimiter.length());//将最后的分隔符去掉
        System.out.println(sb.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testJoiners2(){
//      The result is built by calling Object.toString() for each element that was passed
//      in. As a consequence, if the skipNulls or useForNull method is not used, a NullPointerException
//      will be thrown.

        String join = Joiner.on("-").join(null, 2, 3);
        System.out.println(join);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testJoiners3(){
//      useForNull() 和 skipNulls() 不能同时使用，否则抛异常
        Joiner joiner = Joiner.on("-").skipNulls().useForNull("missing");
        System.out.println(joiner.join(1, null, 2, 3));
    }

    @Test(expected = NullPointerException.class)
    public void testJoiners4(){
//      Once created, a Joiner class is immutable, and therefore thread-safe, and can be
//      used as a static final variable.
        Joiner joiner = Joiner.on("-");//一经创建，无法修改
        joiner.useForNull("missing");//修改无效
        String result = joiner.join(1, null, 2, 3);
        System.out.println(result);
    }

    /**
     * 将 join() 的结果追加到 StringBuilder 上
     */
    @Test
    public void testJoiners5(){
        Joiner joiner = Joiner.on("-").skipNulls();
        StringBuilder builder = new StringBuilder();
        StringBuilder sb = joiner.appendTo(builder, "one", null, "two");
        System.out.println(builder);
        System.out.println(sb);
        System.out.println(sb == builder);//true
    }

    /**
     * 将 join() 的结果追加到 Appendable 上
     */
    @Test
    public void testJoiners6(){
        Joiner joiner = Joiner.on("-").skipNulls();

//      使用 Appendable 的实现类：FileWriter
        try {
            FileWriter fw = new FileWriter(new File("test.txt"));
            FileWriter fileWriter = joiner.appendTo(fw, 1, 2, null, 3);//追加到输出流中

            System.out.println(fw == fileWriter);//true

            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMapJoiner(){
        Map map = Maps.newLinkedHashMap();//为了保持 Map 按照定义的键值对顺序来拼接字符串
        map.put("one", "first");
        map.put("two", "second");
        map.put("three", "third");
        map.put("four", "fourth");
        String join = Joiner.on("#").withKeyValueSeparator("=").join(map);

        String expectedStr = "one=first#two=second#three=third#four=fourth";

//      Matcher 可以由 org.hamcrest.CoreMatchers, org.junit.matchers.JUnitMatchers 的静态方法获取
        Assert.assertThat(join, CoreMatchers.is(expectedStr));
    }

    @Test
    public void testSplitters(){
//      使用 JDK 自带的 split():
        String str = "Monday, Tuesday, Wednesday, Thursday, Friday, , ";
        String[] parts = str.split(",");
        System.out.println(parts.length);//7

        String str2 = "Monday,Tuesday,Wednesday,Thursday,Friday,,";
        String[] parts2 = str2.split(",");
        System.out.println(parts2.length);//5, JDK split() 将空字符串自动忽略了，这个本该交给程序员决定的行为有时并不合适，使用 Guava 就可以解决这个问题

//      使用 Guava 的 Splitter 分割 CharSequence 的子类，如：String, StringBuilder, StringBuffer 或 CharArray / CharBuffer
//      on() 的参数可以是：char, CharMatcher, String, Pattern
        Iterable<String> result = Splitter.on("-").split("1-2-3");
        for (String s : result) {
            System.out.println(s);
        }

//      A Splitter class can split on:
//          1. a single character
//          2. a fixed string
//          3. a java.util.regex.Pattern
//          4. a string representing a regular expression
//          5. CharMatcher

//        split on one or more consecutive digits embedded in a string
//        Splitter splitter = Splitter.on("\\d+");//实测无效，没有当作 regex 来解析
//        Splitter splitter = Splitter.onPattern("\\d+");//ok, regex 专用方法
        Splitter splitter = Splitter.on(Pattern.compile("\\d+"));//和上面等价
        String testStr = "hello88world9this3is43333China";
        Iterable<String> result2 = splitter.split(testStr);
        for (String s : result2) {
            System.out.println(s);
        }

        String[] split = testStr.split("\\d+");
        System.out.println(split.length);//5
    }

    @Test
    public void testSplitter2(){
//      Splitter 和 Joiner 类似，一经创建
        Splitter splitter = Splitter.on(Pattern.compile("\\d+"));//和上面等价
        String testStr = "hello43333  China  ";

        splitter.trimResults();//invalid
        for (String s : splitter.split(testStr)) {
            System.out.println(s);
        }

//      因此，最好一次性创建完毕，否则后续修改无效
//        Splitter splitter1 = Splitter.onPattern("\\d+").trimResults();
        Splitter splitter1 = Splitter.onPattern("\\d+").trimResults(CharMatcher.WHITESPACE);

        Iterable<String> split = splitter1.split(testStr);
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void testMapSplitter(){
        Splitter.MapSplitter mapSplitter = Splitter.on("#").withKeyValueSeparator("=");
        String str = "1=one#2=two#3=three";

        Map<String, String> map = mapSplitter.split(str);//接收的参数是：CharSequence, 如：String/StringBuilder/StringBuffer/CharBuffer/CharArray

        System.out.println(map.getClass().getSimpleName());//UnmodifiableMap

//      Map 中的键值对保持字符串中的顺序
        Map<String, String> mapExpected = Maps.newLinkedHashMap();
        mapExpected.put("1", "one");
        mapExpected.put("2", "two");
        mapExpected.put("3", "three");

        Assert.assertThat(map, CoreMatchers.is(mapExpected));
    }

    @Test
    public void testCharSet(){
//      常见的操作有：
        "hello".getBytes();//不指定字符集，使用系统默认的字符集

//      最好指定字符集：
        "hello".getBytes(StandardCharsets.UTF_8);//JDK 7 提供了字符集常量
        "hello".getBytes(Charsets.UTF_8);//Guava 提供的字符集常量
    }

    @Test
    public void testStrings(){
//      使用字符串进行补位
//      1. JDK 常见操作
        StringBuilder sb = new StringBuilder("foo");
        char c = 'x';//字符需要使用单引号，字符串使用双引号
        for (int i = 0; i < 3; i++) {
            sb.append(c);
        }
        Assert.assertEquals("fooxxx", sb.toString());

//      2. 使用 Guava 的 Strings 实现
//      the second argument specifies the minimum length of the returned string
//      and not how many times to append the third argument to the original string
        String s = Strings.padEnd("foo", 6, 'x');//不足6位的使用 'x' 补足
        Assert.assertEquals("fooxxx", s);

//      there's a corresponding padStart method with the same signature and behaviour
//      with the exception that the character is inserted in front of the given string
//      until the minimum length is met.
        String s1 = Strings.padStart("foo", 6, 'x');
        Assert.assertEquals("xxxfoo", s1);

//      OTHER USEFUL METHODS:
        Assert.assertEquals("", Strings.nullToEmpty(null));//it's probably a good idea to always use the nullToEmpty method on any string objects passed as arguments.
        Assert.assertEquals(null, Strings.emptyToNull(""));

        Assert.assertTrue(Strings.isNullOrEmpty(null));
        Assert.assertTrue(Strings.isNullOrEmpty(""));
    }

    @Test
    public void testCharMatcher(){
//        take a string that spans multiple lines and format it to be on
//        one line with a space where the line break was previously present
        String testStr = "hello\n\r\n\rworld";
        String expectedStr = "hello world";

//      将换行符替换为空格
        String s = CharMatcher.BREAKING_WHITESPACE.replaceFrom(testStr, " ");//hello    world

//      去除单词间多余的空格，本质：regex replacement
        String scrubbed = CharMatcher.WHITESPACE.collapseFrom(s, ' ');//"hello world"
        Assert.assertThat(expectedStr, CoreMatchers.is(scrubbed));

//      去除：1. 首尾的空格；2. 压缩单词间的空格
        String testStr2 = "  hello  world  ";
        String expectedStr2 = "hello world";

        String s1 = CharMatcher.WHITESPACE.trimAndCollapseFrom(testStr2, ' ');
        Assert.assertThat(s1, CoreMatchers.is(expectedStr2));

//      保留字符串中的一部分
        String foo = "foo123bar45goo67";
        String exp = "1234567";
        String s2 = CharMatcher.JAVA_DIGIT.retainFrom(foo);
        Assert.assertThat(s2, CoreMatchers.is(exp));

//      CharMatch 的自由组合
        CharMatcher numOrLetter = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LETTER);
        String s3 = numOrLetter.retainFrom("!@jdk7()");
        Assert.assertThat(s3, CoreMatchers.is("jdk7"));

        CharMatcher upperLetter = CharMatcher.JAVA_LETTER.and(CharMatcher.JAVA_UPPER_CASE);
        String s4 = upperLetter.retainFrom("12HELLOworld");
        Assert.assertThat(s4, CoreMatchers.is("HELLO"));
    }

    /**
     * 测试：代码执行的先决条件
     */
    @Test(expected = NullPointerException.class)
    public void testPreConditions(){
//      1. 自己实现的参数检查
//        if(arg == null){
//            throw new IllegalArgumentException("参数不能为 NULL");
//        }

//      2. 使用 Guava
        Object fakeArg = null;
        Object validatedArg = Preconditions.checkNotNull(fakeArg, "参数不能为 NULL");
        System.out.println(validatedArg);
    }
}
