package com.example.dell.chihuobao.bean;

import java.util.HashMap;

/**
 * Created by fanghao on 2016/3/17.
 */
public class User {
    String token;
    String status;
    HashMap  user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HashMap getUser() {
        return user;
    }

    public void setUser(HashMap user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
