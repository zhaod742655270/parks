package com.hbyd.parks.attendancesys.util;

import java.util.Date;

/**
 * Created by allbutone on 14-8-6.
 */
public class TestBean {
    private int id;
    private Date date;
    private String name;

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
