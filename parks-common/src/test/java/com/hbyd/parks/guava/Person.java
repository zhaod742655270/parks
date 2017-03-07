package com.hbyd.parks.guava;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * Created by allbutone on 2014/11/5.
 */
public class Person implements Comparable<Person>{
    private int id;
    private String name;
    private int age;

    Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("age", age)
                .toString();
    }

    @Override
    public int compareTo(Person o) {
//        类似的也有 ComparatorChain
        return ComparisonChain.start()
                .compare(this.id, o.getId())
                .compare(this.name, o.getName())
                .compare(this.age, o.getAge())
                .result();
    }
}