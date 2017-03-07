package com.hbyd.parks.attendancesys.SPEL;

/**
 * Created by allbutone on 2014/10/15.
 */
public class Student {
    private int id;
    private String name;
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
