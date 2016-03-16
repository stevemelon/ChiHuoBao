package com.example.dell.chihuobao.bean;

import java.util.Objects;

/**
 * Created by dell on 2016/3/16.
 */
public class FoodCategory extends Object {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
