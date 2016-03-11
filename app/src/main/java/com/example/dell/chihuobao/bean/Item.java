package com.example.dell.chihuobao.bean;

/**此类事Order订单对象中的item实体类
 * Created by Zx on 2016/3/9.
 */
public class Item {
    private String item_cout;
    private String item_price;
    private String item_name;

    public void setItem_cout(String item_cout) {
        this.item_cout = item_cout;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_cout() {
        return item_cout;
    }

    public String getItem_price() {
        return item_price;
    }

    public String getItem_name() {
        return item_name;
    }
}
