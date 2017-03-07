package com.hbyd.parks.common.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import javax.annotation.Nullable;

/**
 * SQL 相关的辅助方法
 */
public class SqlHelper {
    /**
     * get content after keyword 'in'
     *
     * @param candidates 字段候选集合，集合内的 null 元素将被忽略
     * @return in 后面的括号内容，包括括号本身
     */
    public static String getStringCandidadtes(Iterable<String> candidates) {
        Iterable<String> singleQuotedItems = Iterables.transform(candidates, new Function<String, String>() {
            @Nullable
            @Override
            public String apply(String input) {
                if(input == null){
                    return null;
                }
                return "'" + input + "'";
            }
        });

        return "(" + Joiner.on(",").skipNulls().join(singleQuotedItems) + ")";
    }

    /**
     * get content after keyword 'in'
     *
     * @param candidates 字段候选集合，集合内的 null 元素将被忽略
     * @return in 后面的括号内容，包括括号本身
     */
    public static String getIntCandidates(Iterable<Integer> candidates) {
        // in java 8:
        // strList.stream().map(s -> "'" + s + "'").collect(Collectors.joining(","));
        return "(" + Joiner.on(",").skipNulls().join(candidates) + ")";
    }
}
