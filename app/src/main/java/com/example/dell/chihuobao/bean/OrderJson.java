package com.example.dell.chihuobao.bean;

import java.util.List;

/**解析order的json实体类
 * Created by dell on 2016/3/14.
 */
public class OrderJson {
    private List<Order> result;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<Order> getRows() {
        return result;
    }
    public void setRows(List<Order> rows) {
        this.result = rows;
    }

}
