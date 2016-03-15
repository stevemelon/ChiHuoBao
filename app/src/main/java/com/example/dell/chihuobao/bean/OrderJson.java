package com.example.dell.chihuobao.bean;

import java.util.List;

/**解析order的json实体类
 * Created by dell on 2016/3/14.
 */
public class OrderJson {
    private List<Order> rows;
    private int total;
    public List<Order> getRows() {
        return rows;
    }

    public void setRows(List<Order> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
