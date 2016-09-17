package com.chenyuwei.requestmaker.retrofit;

import java.io.Serializable;

/**
 * Created by 晶 on 2016/3/16.
 */
public class Person implements Serializable{
    private String name;
    private int age;

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
}
