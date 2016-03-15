package com.example.dell.chihuobao.bean;


/**此类事Order订单对象中的item实体类
 * Created by Zx on 2016/3/9.
 */
public class Item {
    private String productnum;
    private String productprice;
    private String productname;

    public String getProductnum() {
        return productnum;
    }

    public void setProductnum(String productnum) {
        this.productnum = productnum;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
