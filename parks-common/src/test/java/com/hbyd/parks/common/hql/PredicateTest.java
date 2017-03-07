package com.hbyd.parks.common.hql;

import org.junit.Test;

import java.util.Arrays;

import static com.hbyd.parks.common.hql.Predicate.*;

public class PredicateTest {
    @Test
    public void testPredicate(){
        HqlQuery q = new HqlQuery("from Person");

        q.and(eq("name", "jack"));
        q.and(or(like("name", "tom"), le("age", "112")));
        q.or(and(like("name", "david"), le("age", "10")));

        System.out.println(q.getQueryString());
        System.out.println(Arrays.asList(q.getParametersValue()));

//        from Person  WHERE (name = ?) AND ((name like ? or age <= ?)) OR ((name like ? and age <= ?))
//        [jack, tom, 112, david, 10]
    }
}