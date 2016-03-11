package com.example.dell.chihuobao.bean;

/**
 * Created by dell on 2016/3/1.
 */
public class Order {
    private String order_search_result_item_id;
    private String telephone;
    private String address;
    private String orderId;
    private String item_price;
    private String item_count;
    private String item_name;
    private String notice;
    private String receipt;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    private String time;
    private Item item;

    public String getOrder_search_result_item_id() {
        return order_search_result_item_id;
    }

    public String getTime() {
        return time;
    }

    public String getTelphone() {
        return telephone;
    }

    public String getAdddress() {
        return address;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItem_price() {
        return item_price;
    }

    public String getItem_count() {
        return item_count;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getNotice() {
        return notice;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setOrder_search_result_item_id(String order_search_result_item_id) {
        this.order_search_result_item_id = order_search_result_item_id;
    }

    public void setTelphone(String telphone) {
        this.telephone = telphone;
    }

    public void setAdddress(String adddress) {
        this.address = adddress;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
