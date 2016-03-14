package com.example.dell.chihuobao.util;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    private static final String localhost = "";

    public static String getLocalhost() {
        return localhost;
    }

    // 在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }
}
