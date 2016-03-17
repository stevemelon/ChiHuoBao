package com.example.dell.chihuobao.bean;

import java.util.HashMap;

/**
 * Created by fanghao on 2016/3/17.
 */
public class User {
    String token;
    String status;
    HashMap<String,Object> info;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, Object> getInfo() {
        return info;
    }

    public void setInfo(HashMap<String, Object> info) {
        this.info = info;
    }
}
