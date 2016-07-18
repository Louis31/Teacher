package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 订单地址信息bean
 * Created by hyming on 2016/7/14.
 */
public class AddressInfo implements Serializable {
    private int uid;
    private String uname;// 用户名
    private String phone;// 电话
    private String province;// 省份
    private String dist;// 目的地
    private String city;// 城市
    private String poscode;
    private String addrs;   // 地址


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPoscode() {
        return poscode;
    }

    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    public String getAddrs() {
        return addrs;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }
}
