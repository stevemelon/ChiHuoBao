package com.example.dell.chihuobao.bean;

import java.util.List;

/**解析order的json实体类
 * Created by dell on 2016/3/14.
 */
public class OrderJson {
    private List<Order> result;
    public List<Order> getRows() {
        return result;
    }
    public void setRows(List<Order> rows) {
        this.result = rows;
    }

}
