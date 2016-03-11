package com.example.dell.chihuobao.util;

import android.util.Log;

/**
 * Created by fanghao on 2016/3/3.
 * Log封装类
 */
public class BaseLog {
    //Info
    public static void i(String tag,String msg) {
        Log.i(tag, msg);
    }
    public static void i(String msg) {
        Log.i("Info", msg);
    }
    //error
    public static void e(String tag,String msg) {
        Log.e(tag, msg);
    }
    public static void e(String msg) {
        Log.e("error", msg);
    }
    //Debug
    public static void d(String tag,String msg) {
        Log.d(tag, msg);
    }
    public static void d(String msg) {
        Log.d("Debug", msg);
    }
    //Warning
    public static void w(String tag,String msg) {
        Log.w(tag, msg);
    }
    public static void w(String msg) {
        Log.w("Warning", msg);
    }
    //Verbose
    public static void v(String tag,String msg) {
        Log.v(tag, msg);
    }
    public static void v(String msg) {
        Log.v("Verbose", msg);
    }

}
