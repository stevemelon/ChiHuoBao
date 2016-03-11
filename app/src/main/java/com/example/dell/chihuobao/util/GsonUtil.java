package com.example.dell.chihuobao.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by fanghao on 2016/3/6.
 */
public class GsonUtil {
    /**
     *
     * 函数名称: parseData
     * 函数描述: 将json字符串转换为map
     * @param data
     * @return
     */
    public static HashMap<String, String> parseData(String data){
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        HashMap<String, String> map = g.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
        return map;
    }
}
