package com.example.dell.chihuobao.bean;

import android.widget.ImageView;

/**
 * Created by dell on 2016/2/27.
 */
public class Food {
    private String id;
    private String name;
    private String categoryid;
    private String photo;
    private String photodetail;
    private String price;
    private String description;
    private String shopid;
    private String rank;
    private String inserttime;
    private String storenumber;
    private String status;
    private String achievemoney;
    private String reducemoney;
    private String salescount;

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", categoryid='" + categoryid + '\'' +
                ", photo='" + photo + '\'' +
                ", photodetail='" + photodetail + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", shopid='" + shopid + '\'' +
                ", rank='" + rank + '\'' +
                ", inserttime='" + inserttime + '\'' +
                ", storenumber='" + storenumber + '\'' +
                ", status='" + status + '\'' +
                ", achievemoney='" + achievemoney + '\'' +
                ", reducemoney='" + reducemoney + '\'' +
                ", salescount='" + salescount + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotodetail() {
        return photodetail;
    }

    public void setPhotodetail(String photodetail) {
        this.photodetail = photodetail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getStorenumber() {
        return storenumber;
    }

    public void setStorenumber(String storenumber) {
        this.storenumber = storenumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAchievemoney() {
        return achievemoney;
    }

    public void setAchievemoney(String achievemoney) {
        this.achievemoney = achievemoney;
    }

    public String getReducemoney() {
        return reducemoney;
    }

    public void setReducemoney(String reducemoney) {
        this.reducemoney = reducemoney;
    }

    public String getSalescount() {
        return salescount;
    }

    public void setSalescount(String salescount) {
        this.salescount = salescount;
    }
}
