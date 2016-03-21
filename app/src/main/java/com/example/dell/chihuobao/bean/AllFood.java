package com.example.dell.chihuobao.bean;

import java.util.ArrayList;

/**
 * Created by dell on 2016/3/18.
 */
public class AllFood {
    private FoodCategory foodCategory;
    private ArrayList<Food> foodArrayList;

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public ArrayList<Food> getFoodArrayList() {
        return foodArrayList;
    }

    public void setFoodArrayList(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    @Override
    public String toString() {
        return "AllFood{" +
                "foodCategory=" + foodCategory +
                ", foodArrayList=" + foodArrayList +
                '}';
    }
}
