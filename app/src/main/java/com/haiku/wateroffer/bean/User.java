package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 用户信息bean
 * Created by hyming on 2016/7/7.
 */
public class User implements Serializable {
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
