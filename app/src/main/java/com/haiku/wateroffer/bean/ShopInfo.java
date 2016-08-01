package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 店铺信息
 * Created by hyming on 2016/7/25.
 */
public class ShopInfo implements Serializable {
    private int uid;
    private String icon;
    private String shopName;
    private String area;// 街道
    private String floorDetail;// 楼层
    private String lat;// 经度
    private String lng;// 纬度
    private String range;// 配送距离
    private String phone;
    private String qq;
    private String status;// 店铺营业状态（0：营业中，1：打烊）
    private String userType;// 是否新用户（0：新用户，1：非新用户）


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFloorDetail() {
        return floorDetail;
    }

    public void setFloorDetail(String floorDetail) {
        this.floorDetail = floorDetail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
