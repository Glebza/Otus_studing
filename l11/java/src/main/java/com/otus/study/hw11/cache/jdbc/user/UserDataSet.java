package com.otus.study.hw11.cache.jdbc.user;

import javax.persistence.*;

/**
 * Created by Glebza on 27.07.2017.
 */
@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
   // @GeneratedValue(strategy=GenerationType.IDENTITY)
   // private static long increment = 0;


    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "age", nullable = false, unique = true)
    private int age;

    public UserDataSet(long id, String name, int age) {
        setId(id);
        this.age = age;
        this.name = name;
    }
    public UserDataSet(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public UserDataSet(){}



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
        return "UserDataSet: Name = " + getName() + ", age = " + getAge() + ", id= " + getId();
    }
}
