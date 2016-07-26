package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 用户信息bean
 * Created by hyming on 2016/7/7.
 */
public class User implements Serializable {
    private int uid;
    private String usertype;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public boolean isNew() {
        return usertype.equals("new");
    }
}
