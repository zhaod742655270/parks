package com.hbyd.parks.guava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import com.google.common.primitives.Chars;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import javax.annotation.Nullable;
import java.util.*;

/**
 * com.google.common.collect
 */
public class CollectionTest {

    private ArrayList<Book> books;
    private Book b1;
    private Book b2;
    private Person jack;
    private Person tom;

    @Before
    public void init() {
        jack = new Person(1, "jack");
        b1 = new Book(jack, "Gone With The Wind", "packet", 12d, "isbn_1");
        tom = new Person(2, "tom");
        b2 = new Book(tom, "Red", "O'Reilly", 105d, "isbn_2");
        books = Lists.newArrayList(b1, b2);
    }

    /**
     * 测试 Guava 提供的 Chars, Ints, Longs
     * Apache Commons Collections 提供了 Collections.min/max(arr)
     */
    @Test
    public void testXXXs(){
        int[] arr = {1,2,3,4,6,5};
        int max = Ints.max(arr);
        int min = Ints.min(arr);
        System.out.println(min);
        System.out.println(max);

        char[] charArr = {'1', '2', '3', 'b', 'a'};
        char max1 = Chars.max(charArr);
        char min1 = Chars.min(charArr);
        System.out.println(min1);
        System.out.println(max1);

        long[] lArr = {1l, 2l, 100l, -10l};
        long max2 = Longs.max(lArr);
        long min2 = Longs.min(lArr);
        System.out.println(max2);
        System.out.println(min2);
    }

    /**
     * The FluentIterable class presents a powerful interface for working with Iterable
     * instances in the fluent style of programming. The fluent programming style allows
     * us to chain method calls together, making for a more readable code.
     * <p/>
     * Iterables is a utility class for working with Iterable instances.
     */
    @Test
    public void testFluentIterable() {
//      1.FluentIterable - since Guava 12.0
        FluentIterable<Book> result = FluentIterable.from(books).filter(new Predicate<Book>() {
            @Override
            public boolean apply(@Nullable Book input) {
                return input.getPrice() > 100;
            }
        });

//      使用 Iterables 完成同等实现
//      2.Iterables - since Guava 2.0
//        Iterable<Book> result = Iterables.filter(books, new Predicate<Book>() {
//            @Override
//            public boolean apply(@Nullable Book input) {
//                return input.getPrice() > 100;
//            }
//        });

        Assert.assertThat(result, JUnitMatchers.hasItem(b2));
        Assert.assertThat(Iterables.contains(result, b1), CoreMatchers.is(false));
        Assert.assertThat(Iterables.contains(result, b2), CoreMatchers.is(true));

//      If no objects satisfy the Predicate, an empty Iterable will be returned.
        Iterable<Book> result2 = Iterables.filter(books, new Predicate<Book>() {
            @Override
            public boolean apply(@Nullable Book input) {
                return input.getPrice() > 200;
            }
        });
        Assert.assertThat(result2.iterator().hasNext(), CoreMatchers.is(false));
    }

    /**
     * The FluentIterable.transform method is a mapping operation where Function
     * is applied to each element.
     * <p/>
     * 类似于 JS 中的 map 操作
     */
    @Test
    public void testFluentIterableTransformToList() {
//      使用 FluentIterable.from().transform() 实现
        ImmutableList<String> bookList = FluentIterable.from(books).transform(new Function<Book, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Book input) {
                return input.getTitle();
            }
        }).toList();

        Assert.assertEquals(2, bookList.size());

//      使用 Iterables.transform 完成同等实现
        Iterable<String> titles = Iterables.transform(books, new Function<Book, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Book input) {
                return input.getTitle();
            }
        });

//      要转换为 List, 需要使用 Lists
        ArrayList<String> titleList = Lists.newArrayList(titles);

        Assert.assertEquals(2, titleList.size());
    }

    /**
     *  The toMap method considers the elements of the FluentIterable instance to be
     *  the keys, and requires Function to map values to those keys.
     */
    @Test
    public void testFluentIterableTransformToMap() {
//        toMap(): 将迭代的元素作为 key, 将 Function 返回的值作为 value;
        ImmutableMap<Book, Person> map = FluentIterable.from(books).toMap(new Function<Book, Person>() {
            @Nullable
            @Override
            public Person apply(@Nullable Book input) {
                return input.getAuthor();
            }
        });

        Assert.assertEquals("tom", map.get(b2).getName());

//      组合使用 filter() transform() 和 toMap();
        ImmutableMap<Person, String> map2 = FluentIterable.from(books).filter(new Predicate<Book>() {
            @Override
            public boolean apply(@Nullable Book input) {
                return input.getPrice() > 100d;//价格大于 100 元的书
            }
        }).transform(new Function<Book, Person>() {
            @Nullable
            @Override
            public Person apply(@Nullable Book input) {
                return input.getAuthor();//书的作者
            }
        }).toMap(new Function<Person, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Person input) {
                return input.getName();
            }
        });

        Assert.assertEquals(1, map2.size());
        Assert.assertEquals(tom.getName(), map2.get(tom));
        System.out.println(map2);
    }

    /**
     * toSet():
     * Returns an ImmutableSet containing all of the elements from this fluent iterable
     * with duplicates removed.
     */
    @Test
    public void testFluentIterableTransformToSet(){
        ArrayList<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5, 1);
        ImmutableSet<Integer> set = FluentIterable.from(ints).toSet();

        Assert.assertEquals(ints.size() - 1, set.size());
    }

    /**
     * FluentIterable.toSortedSet()
     */
    @Test
    public void testFluentIterableToSortedSet(){
        ArrayList<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5, 1);
        ImmutableSortedSet<Integer> sortedSet = FluentIterable.from(ints).toSortedSet(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        });

//        JUnit 如何断言集合元素的顺序？
        System.out.println(sortedSet);//[5, 4, 3, 2, 1]
    }

    /**
     * FluentIterable.toSortedList()
     */
    @Test
    public void testFluentIterableToSortedList(){
        ArrayList<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5);
        ImmutableList<Integer> sortedList = FluentIterable.from(ints).toSortedList(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        });
        System.out.println(sortedList);
    }

    @Test
    public void testFluentIterableCopyInto(){
        ArrayList<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5);
        List<Integer> list = Lists.newArrayList(7, 8);
        FluentIterable.from(ints).copyInto(list);
        System.out.println(list);
    }

    /**
     * Lists.partition(): 分割列表为指定容量的子列表，元素顺序不变
     */
    @Test
    public void testListsPartition(){
        ArrayList<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5);
        List<List<Integer>> partition = Lists.partition(ints, 3);
        Assert.assertEquals(3, partition.get(0).size());
        Assert.assertEquals(2, partition.get(1).size());//剩余元素单独一个子列表
    }

    /**集合差异
     * The Sets.difference method takes two set instance parameters and returns
     * SetView of the elements found in the first set, but not in the second. SetView is a
     * static, abstract inner class of the Sets class and represents an unmodifiable view of
     * a given Set instance.
     */
    @Test
    public void testSetsDifference(){
        HashSet<Integer> s1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> s2 = Sets.newHashSet(2, 3, 4);

        Sets.SetView<Integer> difference = Sets.difference(s1, s2);

        Assert.assertEquals(1, difference.size());
        Assert.assertTrue(difference.contains(1));//s1 中有的但 s2 中没有的
        for (Integer integer : difference) {
            System.out.println("integer = " + integer);
        }

        Sets.SetView<Integer> difference1 = Sets.difference(s2, s1);

        Assert.assertEquals(1, difference1.size());
        Assert.assertTrue(difference1.contains(4));
        for (Integer integer : difference1) {
            System.out.println("integer = " + integer);
        }
    }

    /**集合的对称差异
     * The Sets.symmetricDifference method returns elements that are contained in one
     * set or the other set, but not contained in both.
     */
    @Test
    public void testSetsSymmetricDifference(){
        HashSet<Integer> s1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> s2 = Sets.newHashSet(2, 3, 4);

        Sets.SetView<Integer> integers = Sets.symmetricDifference(s1, s2);
        Assert.assertEquals(2, integers.size());
        Assert.assertThat(integers, JUnitMatchers.hasItems(1, 4));
    }

    /**
     * 集合的交集
     * The Sets.intersection method returns an unmodifiable SetView instance
     * containing elements that are found in two Set instances.
     */
    @Test
    public void testSetsIntersection(){
        HashSet<Integer> s1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> s2 = Sets.newHashSet(2, 3, 4);

        Sets.SetView<Integer> intersection = Sets.intersection(s1, s2);
        Assert.assertEquals(2, intersection.size());
        Assert.assertThat(intersection, JUnitMatchers.hasItems(2, 3));
    }

    /**
     * 集合的联合
     * The Sets.union method takes two sets and returns a SetView instance that contains
     * elements that are found in either set.
     */
    @Test
    public void testSetsUnion(){
        HashSet<Integer> s1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> s2 = Sets.newHashSet(2, 3, 4);

        Sets.SetView<Integer> union = Sets.union(s1, s2);
        Assert.assertThat(union.size()==4
                            && union.contains(1)
                            && union.contains(2)
                            && union.contains(3)
                            && union.contains(4), CoreMatchers.is(true));
    }

    /**
     * Maps.uniqueIndex(Iterable, Function());
     *      Returns an immutable map for which the Map.values() are the given elements
     *      in the given order, and each key is the product of invoking a supplied
     *      function on its corresponding value
     *
     *      使用 Function 为 Iterable 中每个元素生成唯一索引作为 key, 元素本身作为 value
     */
    @Test
    public void testMapsUniqueIndex(){
        /*
             if we were to have Function passed in either by a method call or with
             dependency injection, we could easily change the algorithm for generating
             the key for the Book object, with no impact to the surrounding code.
        */
        ImmutableMap<String, Book> map = Maps.uniqueIndex(books, new Function<Book, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Book input) {
                return input.getIsbn();
            }
        });

        Assert.assertEquals(b1, map.get("isbn_1"));
    }

    /**
     * Maps.asMap():
     * While the Maps.uniqueIndex method uses Function to generate keys from the
     * given values, the Maps.asMap method does the inverse operation. The Maps.asMap
     * method takes a set of objects to be used as keys, and Function is applied to each key
     * object to generate the value for entry into a map instance. There is another method,
     * Maps.toMap, that takes the same arguments with the difference being ImmutableMap
     * is returned instead of a view of the map. The significance of this is that the map
     * returned from the Maps.asMap method would reflect any changes made to the
     * original map, and the map returned from the Maps.toMap method would remain
     * unchanged from changes to the original map.
     *
     * Maps.asMap() 将迭代的元素作为 key, Function(key) 的返回值作为 value
     *
     * 类似：FluentIterable.toMap
     */
    @Test
    public void testMapsAsMap(){
        HashSet<Book> bs2 = Sets.newHashSet(b1, b2);
        Map<Book, String> map = Maps.asMap(bs2, new Function<Book, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Book input) {
                return input.getTitle();
            }
        });

        Assert.assertEquals("Red", map.get(b2));
    }

    /**
     * Maps.transformEntries()
     * Returns a view of a map whose values are derived from the original map's entries.
     * 1. Map.Entry: (key, oldValue) -> (key, newValue)
     *      newValue = Function(key, oldValue);
     * 2. 返回的是基于原来 Map 的视图
     */
    @Test
    public void testMapsTransformEntries(){

        HashMap<String, Book> map = Maps.newHashMap();
        map.put(b1.getIsbn(), b1);
        map.put(b2.getIsbn(), b2);

        System.out.println(map);

        Map<String, String> map2 = Maps.transformEntries(map, new Maps.EntryTransformer<String, Book, String>() {
            @Override
            public String transformEntry(@Nullable String key, @Nullable Book value) {
                return key + "-" + value.getTitle();
            }
        });

        System.out.println(map2);

//      Maps.transformEntries() 得到的 Map 是 "懒计算" 的结果
        map.remove(b1.getIsbn());
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(1, map2.size());//map2 和 map 的 size 始终一致
    }

    /**
     * Maps.transformValues
     * Returns a view of a map where each value is transformed by a function.
     * All other properties of the map, such as iteration order, are left intact.
     *
     * 1. Map.Entry: (key, oldValue) -> (key, newValue)
     *      newValue = Function(oldValue);
     * 2. 返回的是基于原来 Map 的视图
     */
    @Test
    public void testMapsTransformValues(){
        HashMap<String, Book> map = Maps.newHashMap();
        map.put(b1.getIsbn(), b1);
        map.put(b2.getIsbn(), b2);

        Map<String, String> map2 = Maps.transformValues(map, new Function<Book, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Book input) {
                return input.getTitle();
            }
        });

        Assert.assertEquals(2, map.size());
        Assert.assertEquals(2, map2.size());

//      Maps.transformValue() 返回的是基于原来 Map 的视图
        map.remove(b1.getIsbn());

        Assert.assertEquals(1, map.size());
        Assert.assertEquals(1, map2.size());
    }

    /**
     * While maps are great data structures that are used constantly in programming,
     * there are times when programmers need to associate more than one value with a
     * given key. While we are free to create our own implementations of maps that have
     * a list or set as a value, Guava makes it much easier. The static factory methods return
     * map instances that give us the familiar semantics of the put(key,value) operation.
     * The details of checking if a collection exists for the given key and creating one if
     * necessary, then adding the value to that collection, are taken care of for us.
     *
     * MultiMap: 多重映射，一个 key 映射到多个 value
     *
     * ArrayListMultiMap:
     *  Implementation of Multimap that uses an ArrayList to store the values for a given key.
     *  Map<String, ArrayList<String>>
     */
    @Test
    public void testArrayListMultiMap(){
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("1", "one");
        multimap.put("1", "first");
        multimap.put("2", "two");
        multimap.put("2", "second");

        Assert.assertEquals(4, multimap.size());//键值对的容量，而非 key 的容量

        Assert.assertTrue(multimap.containsEntry("1", "one"));
        Assert.assertTrue(multimap.containsKey("1"));
        Assert.assertTrue(multimap.containsValue("first"));

//      第一种断言方式
        Assert.assertEquals(multimap.get("1"), Lists.newArrayList("one", "first"));
        Assert.assertEquals(multimap.get("2"), Lists.newArrayList("two", "second"));
//      第二种断言方式
        Assert.assertThat(multimap.get("1"), JUnitMatchers.hasItems("one", "first"));
        Assert.assertThat(multimap.get("2"), JUnitMatchers.hasItems("two", "second"));

        Map<String, Collection<String>> stringCollectionMap = multimap.asMap();
        Set<String> keys = stringCollectionMap.keySet();
        Assert.assertEquals(2, keys.size());
        Assert.assertThat(keys, JUnitMatchers.hasItems("1", "2"));

//      允许添加重复的键值对
        multimap.put("1", "one");
        multimap.put("2", "two");

        Assert.assertEquals(6, multimap.size());
        Assert.assertThat(multimap.get("1"), JUnitMatchers.hasItems("one", "first"));
        Assert.assertThat(multimap.get("2"), JUnitMatchers.hasItems("two", "second"));

//      a call to values() returns a collection containing all six values, not a
//      collection containing two lists with three elements each. While this may
//      seem puzzling at first, we need to remember that the multimap is not a true map.
        Assert.assertEquals(6, multimap.values().size());
        
//      MultiMap 不是 Map, 可以使用 asMap() 进行转换
        Map<String, Collection<String>> map = multimap.asMap();
        Assert.assertEquals(2, map.size());

//      转换为 Map 后，不再支持 put() 操作
        try {
            map.put("3", Lists.newArrayList("three", "third"));
        } catch (Exception e) {
//          UnsupportedOperationException extends RuntimeException
            Assert.assertThat(e, CoreMatchers.is(UnsupportedOperationException.class));
        }

//      在键值对添加完毕后，使用 trimToSize() 将内存压缩到实际大小
        ArrayListMultimap<Object, Object> multiMap2 = ArrayListMultimap.create(10, 3);//10 个键值对，每个 key 对应的 value 容量为 3

        multiMap2.put("1", "one");

        System.out.println(multiMap2.size());//1
        multimap.trimToSize();
        System.out.println(multiMap2.size());//1, multiMap 的容量没有变化，但内存占用降低了。
    }

    /**
     * HashMultimap is based on hash tables. Unlike ArrayListMultimap, inserting
     * the same key-value pair multiple times is not supported.
     *
     * Before we move on, it's worth mentioning some of the other implementations
     * of multimap. First, there are three immutable implementations:
     * ImmutableListMultimap, ImmutableMultimap, and ImmutableSetMultimap.
     * There is LinkedHashMultimap, which returns collections for a given key that have
     * the values in the same order as they were inserted. Finally, we have TreeMultimap
     * that keeps the keys and values sorted by their natural order or the order specified by
     * a comparator.
     */
    @Test
    public void testHashMultiMap(){
        HashMultimap<Object, Object> multimap = HashMultimap.create();

        multimap.put("1", "one");
        multimap.put("1", "one");//ignored
        multimap.put("1", "first");
        multimap.put("2", "two");
        multimap.put("2", "two");//ignored
        multimap.put("2", "second");

//      Assert only distinct key-value pairs are kept.
        Assert.assertEquals(4, multimap.size());
    }

    /**
     * Next to being able to have multiple values for a key in a map, is the ability to
     * navigate from a value to a key in a map. The bimap gives us that functionality.
     * The bimap is unique, in that it keeps the values unique in the map as well as the
     * keys, which is a prerequisite to invert　the map and navigate from a value to a key.
     *
     * BiMap 的 key 和 value 都是唯一的，这样才能支持 key->value 和 value->key 的双向映射
     *
     */
    @Test
    public void testBiMap(){
        HashBiMap<Object, Object> biMap = HashBiMap.create();
        biMap.put("1", "one");
        try {
//          when using a bimap, inserting a new key with a value that already exists in
//          the map causes IllegalArgumentException to be thrown.
            biMap.put("2", "one");
        } catch (Exception e) {
            Assert.assertThat(e, CoreMatchers.is(IllegalArgumentException.class));
        }

//        key -> value
        Assert.assertEquals("one", biMap.get("1"));

//        value -> key
        BiMap<Object, Object> iBiMap = biMap.inverse();
        Assert.assertEquals("1", iBiMap.get("one"));

        System.out.println(biMap);//{1=one}
        System.out.println(iBiMap);//{one=1}
    }

    /**
     * In order to add the same value with a different key, we need to call
     * forcePut(key,value). The BiMap.forcePut call will quietly remove the map entry
     * with the same value before placing the key-value pair in the map.
     *
     * Although we only covered the HashBiMap method here, there are also implementations
     * of EnumBiMap, EnumHashBiMap, and ImmutableBiMap.
     */
    @Test
    public void testBiMapForcePut(){
        HashBiMap<Object, Object> biMap = HashBiMap.create();
        biMap.put("1", "one");
        biMap.forcePut("2", "one");//替换同值的键值对
        biMap.put("2", "two");//更新键值

        Assert.assertThat(biMap.containsKey("1"), CoreMatchers.is(false));
        Assert.assertThat(biMap.containsKey("2"), CoreMatchers.is(true));

        Assert.assertThat(biMap.containsValue("one"), CoreMatchers.is(false));
        Assert.assertThat(biMap.containsValue("two"), CoreMatchers.is(true));
    }

    /**
     * A table is a collection that takes two keys, a row, and a column, and maps
     * those keys to a single value. While not explicitly called out as a map of
     * maps, however, the table gives us the desired functionality and is much
     * easier to use.
     *
     * <pre>
     * Table 可以指定三个泛型：R,C,V
     *     R - Row
     *     C - Column
     *     V - Value
     * </pre>
     */
    @Test
    public void testTable(){
//      for our examples, we will  be working with HashBased Table, which
//      stores data in Map<R, Map<C, V>>.
        HashBasedTable<Integer, Integer, String> table = HashBasedTable.create();
        String put = table.put(1, 1, "1-1");//返回之前 <row,column> 映射的 value
        Assert.assertNull(put);

//      创建 5 行 5 列的表格
        HashBasedTable<Integer, Integer, String> table2 = HashBasedTable.create(5, 5);
        table2.putAll(table);

        Assert.assertTrue(table2.contains(1,1));
        Assert.assertTrue(table2.containsRow(1));
        Assert.assertTrue(table2.containsColumn(1));
        Assert.assertTrue(table2.containsValue("1-1"));

        String val = table2.get(1, 1);
        Assert.assertEquals("1-1", val);

        String removed = table2.remove(1, 1);
        Assert.assertEquals("1-1", removed);
    }

    /**
     * The table provides some great methods for obtaining different views of the
     * underlying data in the table
     *
     * HashBasedTable: Implementation of Table using hash tables.
     * 1. ArrayTable is an implementation of the table backed by a two-dimensional array.
     * 2. There is an ImmutableTable implementation. Since ImmutableTable
     * can't be updated after it's created, the row, key, and values are added
     * using ImmutableTable.Builder, which leverages a fluent interface
     * for ease of construction.
     * 3. A TreeBasedTable table where the row and column keys are ordered,
     * either by the natural order or by specified comparators for the row and
     * column keys.
     */
    @Test
    public void testTableView(){
        HashBasedTable<Integer, Integer, String> table = HashBasedTable.create();
        table.put(1, 1, "1-1");
        table.put(2, 1, "2-1");
        table.put(2, 2, "2-2");

        Map<Integer, String> row = table.row(1);//行视图
        Map<Integer, String> col = table.column(1);//列视图

        Assert.assertEquals(1, row.size());
        Assert.assertEquals(2, col.size());

        row.put(1, "new 1-1");//视图变化会反映到 Table 中
        col.put(2, "new 2-1");//视图变化会反映到 Table 中

        Assert.assertEquals("new 1-1", table.get(1,1));
        Assert.assertEquals("new 2-1", table.get(2,1));

//      Table 变化会反映到视图
        Map<Integer, String> row2 = table.row(2);
        table.put(2,2,"new 2-2");
        Assert.assertEquals("new 2-2", row2.get(2));
    }

    /**
     * ArrayTable 是通过如下几个字段实现的：
     * //存储行 key
     * private final ImmutableList<R> rowList;
     * //存储列 key
     * private final ImmutableList<C> columnList;
     * //存储行 key 在 rowList 中的索引
     * private final ImmutableMap<R, Integer> rowKeyToIndex;
     * //存储列 key 在 columnList 中的索引
     * private final ImmutableMap<C, Integer> columnKeyToIndex;
     * //存储实际的 cell value
     * private final V[][] array;
     */
    @Test
    public void testArrayTable(){
        ArrayList<Integer> row = Lists.newArrayList(1, 2);
        ArrayList<Integer> col = Lists.newArrayList(1, 2);

        ArrayTable<Integer, Integer, String> table = ArrayTable.create(row, col);

        table.put(1,1,"1-1");
        table.put(1,2,"1-2");
        table.put(2,1,"2-1");
        table.put(2,2,"2-2");

        Assert.assertEquals(4, table.size());
        Object value = table.at(1, 1);//按照二位数组的索引取值，而非 row_key 和 col_key, 这样速度更快，无需计算 row_key 和 col_key 在 row_list 和 col_list 中的索引
        Assert.assertEquals("2-2", value);

        Assert.assertThat(table.containsRow(1), CoreMatchers.is(true));
        Assert.assertThat(table.containsColumn(1), CoreMatchers.is(true));
        Assert.assertThat(table.containsValue("2-2"), CoreMatchers.is(true));

        ImmutableList<Integer> col_keys = table.columnKeyList();
        ImmutableList<Integer> row_keys = table.rowKeyList();

        Assert.assertThat(col_keys, JUnitMatchers.hasItems(1,2));
        Assert.assertThat(row_keys, JUnitMatchers.hasItems(1,2));

        Map<Integer, Map<Integer, String>> map = table.columnMap();
        Assert.assertThat(map.get(1).get(1), CoreMatchers.is("1-1"));

        Set<Table.Cell<Integer, Integer, String>> cells = table.cellSet();
        for (Table.Cell<Integer, Integer, String> cell : cells) {
            System.out.println(cell.getRowKey() + "\t" + cell.getColumnKey()+ "\t" + cell.getValue());
        }

//      Associates the value null with the specified keys, assuming both keys are valid.
        table.erase(1,1);
        Assert.assertNull(table.get(1,1));

        table.eraseAll();
        for (Table.Cell<Integer, Integer, String> cell : table.cellSet()) {
            Assert.assertNull(table.get(cell.getRowKey(), cell.getColumnKey()));
        }
    }

    @Test
    public void testImmutableTable(){
        ImmutableTable<Integer, Integer, String> table = ImmutableTable.<Integer, Integer, String>builder()
                .put(1, 1, "1-1")
                .put(1, 2, "1-2")
                .put(2, 1, "2-1")
                .put(2, 2, "2-2")
                .orderColumnsBy(new IntegerComparator())//为 col_key 排序
                .orderRowsBy(new IntegerComparator())//为 row_key 排序
                .build();

        for (Table.Cell<Integer, Integer, String> cell : table.cellSet()) {
            System.out.println(cell.getRowKey() + "\t" + cell.getColumnKey() + "\t" + cell.getValue());
        }
    }

    @Test
    public void testTreeBasedTable(){
        TreeBasedTable<Integer, Integer, String> table = TreeBasedTable.create(new IntegerComparator(), new IntegerComparator());
        table.put(1, 1, "1-1");
        table.put(1, 2, "1-2");
        table.put(2, 1, "2-1");
        table.put(2, 2, "2-2");

        for (Table.Cell<Integer, Integer, String> cell : table.cellSet()) {
            System.out.println(cell.getRowKey() + "\t" + cell.getColumnKey() + "\t" + cell.getValue());
        }
    }

    private static class IntegerComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return -o1.compareTo(o2);
        }
    }


    /**
     * The Range class allows us to create a specific interval or span of values with
     * defined endpoints, and works with Comparable types. The Range objects can
     * define endpoints that are either inclusive (closed), which includes the end value
     * of the Range instance, or exclusive (open), which does not include the end value
     * of the Range instance.
     */
    @Test
    public void testRange(){
//      Range<? extends Comparable>
        Range<Integer> range = Range.range(10, BoundType.CLOSED, 20, BoundType.OPEN);
        Assert.assertTrue(range.contains(10));
        Assert.assertFalse(range.contains(20));

        Range<Integer> range1 = Range.closed(10, 20);
        Assert.assertTrue(range1.contains(10));
        Assert.assertTrue(range1.contains(20));

        Range<Integer> range2 = Range.open(10, 20);
        Assert.assertFalse(range2.contains(10));
        Assert.assertFalse(range2.contains(20));

        Range<Integer> range3 = Range.openClosed(10, 20);
        Assert.assertFalse(range3.contains(10));
        Assert.assertTrue(range3.contains(20));

        Range<Integer> range4 = Range.closedOpen(10, 20);
        Assert.assertTrue(range4.contains(10));
        Assert.assertFalse(range4.contains(20));

//      指定下限
        Range<Integer> range5 = Range.downTo(10, BoundType.CLOSED);
        Assert.assertTrue(range5.contains(10));
        Assert.assertFalse(range5.contains(9));
//      指定上限
        Range<Integer> range6 = Range.upTo(20, BoundType.CLOSED);
        Assert.assertTrue(range6.contains(20));
        Assert.assertFalse(range6.contains(21));

//      Returns a range that contains all values greater than or equal to endpoint.
        Range<Integer> range7 = Range.atLeast(10);
        Assert.assertTrue(range7.contains(10));
        Assert.assertFalse(range7.contains(9));

//      Returns a range that contains all values less than or equal to endpoint.
        Range<Integer> range8 = Range.atMost(20);
        Assert.assertTrue(range8.contains(20));
        Assert.assertFalse(range8.contains(21));

//      Returns a range that contains all values strictly less than endpoint.
        Range<Integer> range9 = Range.lessThan(20);
        Assert.assertFalse(range9.contains(20));

//      Returns a range that contains all values strictly greater than endpoint.
        Range<Integer> range10 = Range.greaterThan(10);
        Assert.assertFalse(range10.contains(10));

//      Returns a range that contains every value of type C.
        Range<Integer> all = Range.all();
        Assert.assertThat(all.contains(Integer.MIN_VALUE), CoreMatchers.is(true));
        Assert.assertThat(all.contains(Integer.MAX_VALUE), CoreMatchers.is(true));

//      Returns the minimal range that contains all of the given values. The returned range is closed on both ends.
        Range<Integer> range11 = Range.encloseAll(Lists.newArrayList(1, 2, 3, 4));
        Assert.assertTrue(range11.contains(1));
        Assert.assertTrue(range11.contains(4));
        Assert.assertFalse(range11.contains(0));
        Assert.assertFalse(range11.contains(5));

//      Returns a range that contains only the given value. The returned range is closed on both ends.
        Range<Integer> singleton = Range.singleton(10);
        Assert.assertTrue(singleton.contains(10));
        Assert.assertFalse(singleton.contains(11));
        Assert.assertFalse(singleton.contains(9));
    }

    @Test
    public void testRangeWithArbitraryComparable(){
//      需求：按照年龄过滤 Person
//      1. Person 实现了 Comparable 接口，可以利用其 compareTo(), 但这个方法比较的不是年龄
//      2. Range implements Predicate, 可以组合 Predicate 和 Function 实现过滤：
        Range<Integer> range = Range.closed(20, 30);

//      可以查看 Range() 实现的 apply() 方法
        Predicate<Person> ageFilter = Predicates.compose(range, new Function<Person, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable Person input) {
                return input.getAge();
            }
        });

        Person p1 = new Person(1, "Jack", 18);
        Person p2 = new Person(2, "Tom", 28);
        Person p3 = new Person(3, "David", 38);
        Person p4 = new Person(4, "Jerry", 48);

        Iterable<Person> filterd = Iterables.filter(Lists.newArrayList(p1, p2, p3, p4), ageFilter);

        Assert.assertThat(filterd, JUnitMatchers.hasItem(p2));

        for (Person person : filterd) {
            Assert.assertTrue(range.contains(person.getAge()));
        }
    }

    /**
     * if we don't explicitly have a need for a mutable collection,
     * we should always favor using an immutable one. First of all, immutable collections
     * are completely thread-safe. Secondly, they offer protection from unknown users
     * who may try to access your code. Fortunately, Guava provides a vast selection of
     * immutable collections. As a matter of fact, for each collection type we have covered
     * in this chapter, there is a suitable immutable version.
     */
    @Test
    public void testImmutableCollection(){
//      All of the Guava immutable collections have a static nested Builder class that
//      uses the fluent interface approach to create the desired instance.
        ImmutableListMultimap<Integer, String> multimap = ImmutableListMultimap.<Integer, String>builder()
                .putAll(1, "one", "first")
                .put(2, "two")
                .put(3, "three")
                .build();

//      MultiMap 所有的 put 和 remove 操作都是 @Deprecated 标注的

        Assert.assertEquals("one", multimap.get(1).get(0));
        Assert.assertEquals("first", multimap.get(1).get(1));

        ImmutableListMultimap<String, Integer> inverse = multimap.inverse();
        Assert.assertEquals(new Integer(1), inverse.get("one").get(0));
        Assert.assertEquals(new Integer(1), inverse.get("first").get(0));
    }

    /**
     * The Ordering class provides us with tools
     * that we need for applying different sorting techniques powerfully and concisely.
     * Ordering is an abstract class. While it implements the Comparator interface,
     * Ordering has the compare method declared as abstract.
     *
     * 创建 Ordering 的两种方式：
     * 1. Creating a new instance and providing an implementation for the compare method
     * 2. Using the static Ordering.from() method that creates an instance of the Ordering
     * from an existing Comparator.
     */
    @Test
    public void testOrdering(){
        Ordering<Person> ageOrdering = new Ordering<Person>() {
            @Override
            public int compare(@Nullable Person left, @Nullable Person right) {
//                return left.getAge() - right.getAge();

//              Guava 为原生类型提供了对应的辅助类
                return Ints.compare(left.getAge(), right.getAge());
            }
        };

        Person p1 = new Person(1, "jack", 11);
        Person p2 = new Person(2, "tom", 22);
        Person p3 = new Person(3, "david", 33);
        Person p4 = new Person(4, "jerry", 33);

//      compare()
        Assert.assertTrue(ageOrdering.compare(p1, p2) == -1);
        Assert.assertTrue(ageOrdering.reverse().compare(p1, p2) == 1);//和 Collections.reverseOrder(Comparator) 等价

        ArrayList<Person> personList = Lists.newArrayList(p1, p2, p3, p4);

//      从小到大筛选指定个数的元素
        List<Person> persons = ageOrdering.leastOf(personList, 2);
        Assert.assertTrue(persons.contains(p1));
        Assert.assertTrue(persons.contains(p2));
        Assert.assertFalse(persons.contains(p3));
        Assert.assertFalse(persons.contains(p4));

//      从大到小筛选指定个数的元素
        List<Person> persons1 = ageOrdering.greatestOf(personList, 1);
        Assert.assertTrue(persons1.contains(p3) || persons1.contains(p4));
        Assert.assertFalse(persons1.contains(p1));
        Assert.assertFalse(persons1.contains(p2));

//      最大值和最小值
        Assert.assertThat(ageOrdering.max(personList), JUnitMatchers.either(CoreMatchers.is(p3)).or(CoreMatchers.is(p4)));
        Assert.assertEquals(p1, ageOrdering.min(personList));

//      按照年龄升序排列
        List<Person> sortedCopy = ageOrdering.sortedCopy(personList);
//      查找元素在列表中的索引，列表必须是有序的
        int index = ageOrdering.binarySearch(sortedCopy, p2);
        Assert.assertEquals(1, index);

//      按照年龄升序排列：另一种实现
        List<Person> sortedCopy2 = ageOrdering.leastOf(personList, personList.size());
//      查找元素在列表中的索引，列表必须是有序的
        int index2 = ageOrdering.binarySearch(sortedCopy2, p2);
        Assert.assertEquals(1, index2);

//      追加排序：如果年龄相同，就按照 ID 降序排列
        Ordering<Person> idOrdering = new Ordering<Person>() {
            @Override
            public int compare(@Nullable Person left, @Nullable Person right) {
                return Ints.compare(left.getId(), right.getId());
            }
        };

//      compound() 可以连续调用，实现多条件排序
        Ordering<Person> cpdOrdering = ageOrdering.compound(idOrdering.reverse());
        List<Person> sortedCopy1 = cpdOrdering.sortedCopy(personList);//p1,p2,p4,p3

//      断言排序结果
        Assert.assertEquals(0, cpdOrdering.binarySearch(sortedCopy1, p1));
        Assert.assertEquals(1, cpdOrdering.binarySearch(sortedCopy1, p2));
        Assert.assertEquals(3, cpdOrdering.binarySearch(sortedCopy1, p3));
        Assert.assertEquals(2, cpdOrdering.binarySearch(sortedCopy1, p4));

//      状态断言
        Assert.assertTrue(cpdOrdering.isOrdered(sortedCopy1));//是否有序: 按照迭代顺序，后一个元素大于前一个元素，例如：cpdOrdering.compare(p3, p4)>=0
        Assert.assertTrue(cpdOrdering.isStrictlyOrdered(sortedCopy1));//是否严格排序:按照迭代顺序，后一个元素大于前一个元素，例如：cpdOrdering.compare(p3, p4)>0


//      暂时不清楚这个 explicit() 怎么使用
//        Ordering<Person> explicit = cpdOrdering.explicit(p3, p2);
//        List<Person> sortedCopy3 = explicit.sortedCopy(personList);
//        System.out.println(sortedCopy3);

//      默认忽略 null 元素
        personList.add(null);

//      将 null 排序到首位：treats the null values as less than any other value in the collection
        List<Person> sortedCopy3 = cpdOrdering.nullsFirst().sortedCopy(personList);
        System.out.println(sortedCopy3);

//      将 null 排序到末位
        List<Person> sortedCopy4 = cpdOrdering.nullsLast().sortedCopy(personList);
        System.out.println(sortedCopy4);

//      排序，但返回不可修改的列表副本
        ImmutableList<Person> persons2 = cpdOrdering.immutableSortedCopy(sortedCopy1);
        System.out.println(persons2);

//      Ordering implements Comparator, 因此可以搭配 JDK 的 Collections 使用
//      注意：
//      1. sort() 没有返回值，修改的是 personList 本身
//      2. 集合元素不能为 null, 否则 NullPointerException
        personList.remove(null);
        Collections.sort(personList, cpdOrdering);//p1,p2,p4,p3

//      断言排序结果
        Assert.assertThat(personList.get(2), CoreMatchers.is(p4));
    }
}