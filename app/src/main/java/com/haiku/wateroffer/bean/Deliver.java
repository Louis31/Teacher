package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 配送员实体类
 * Created by hyming on 2016/7/18.
 */
public class Deliver implements Serializable {
    private int diliveryman_id;// 配送员id
    private String diliveryman_name;// 配送员姓名
    private String diliveryman_phone;// 电话号码
    private String diliveryman_status;// 配送员状态(0：为未添加和删除状态，1：为暂停状态，2：为继续状态)

    public int getDiliveryman_id() {
        return diliveryman_id;
    }

    public void setDiliveryman_id(int diliveryman_id) {
        this.diliveryman_id = diliveryman_id;
    }

    public String getDiliveryman_name() {
        return diliveryman_name;
    }

    public void setDiliveryman_name(String diliveryman_name) {
        this.diliveryman_name = diliveryman_name;
    }

    public String getDiliveryman_phone() {
        return diliveryman_phone;
    }

    public void setDiliveryman_phone(String diliveryman_phone) {
        this.diliveryman_phone = diliveryman_phone;
    }

    public String getDiliveryman_status() {
        return diliveryman_status;
    }

    public void setDiliveryman_status(String diliveryman_status) {
        this.diliveryman_status = diliveryman_status;
    }
}
