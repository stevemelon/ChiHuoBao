package com.example.dell.chihuobao.util;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.example.dell.chihuobao.bean.FoodCategory;
import com.example.dell.chihuobao.bean.User;

import org.xutils.x;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    public static final String localhost = "";
    public static User user;
    private static ArrayList<FoodCategory> foodCategoryArrayList =new ArrayList<>();
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    public static User getUser() {
        return user;
    }

    public static ArrayList<FoodCategory> getFoodCategoryArrayList() {
        return foodCategoryArrayList;
    }

    public static void setFoodCategoryArrayList(ArrayList<FoodCategory> foodCategoryArrayList) {
        MyApplication.foodCategoryArrayList = foodCategoryArrayList;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    public static String getLocalhost() {
        return localhost;
    }

    // 在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        JPushInterface.setDebugMode(false);  // 设置是否为Debug模式
        JPushInterface.init(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }
}
