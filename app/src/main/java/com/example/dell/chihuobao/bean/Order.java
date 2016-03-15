package com.example.dell.chihuobao.bean;
import java.util.List;

/**
 * Created by dell on 2016/3/1.
 */
public class Order {
    private String id;//左上角订单标志号
    private String telephone;
    private String address;
    private String orderId;//订单号
    private List<Item> orderdelist;//商品项目
    private String request;//备注
//    private String receipt;
    private String ordertime;
    private int orderstatus;
    private int totalprice;


    public static class shop{
        public String id;
        public String username;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Item> getOrderdelist() {
        return orderdelist;
    }

    public void setOrderdelist(List<Item> orderdelist) {
        this.orderdelist = orderdelist;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(int orderstatus) {
        this.orderstatus = orderstatus;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
}
