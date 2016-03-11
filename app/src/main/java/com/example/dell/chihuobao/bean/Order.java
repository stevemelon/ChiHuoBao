package com.example.dell.chihuobao.bean;
import java.util.List;

/**
 * Created by dell on 2016/3/1.
 */
public class Order {
    private String order_search_result_item_id;
    private String telephone;
    private String address;
    private String orderId;
    private List<Item> item;
    private String notice;
    private String receipt;
    private String time;

    public List<Item> getItem() {
        return item;
    }
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


    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setItem(List<Item> item) {
        this.item = item;
    }
}
