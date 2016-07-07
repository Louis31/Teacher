package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 数据返回结果bean
 * Created by hyming on 2016/7/7.
 */
public class Result<T> implements Serializable {
    private int retcode;
    private String retmsg;
    private T obj;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public T getObject() {


        return obj;
    }
}
