package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * Created by hyming on 2016/8/6.
 */
public class Contribution implements Serializable {
    private int uid;
    private String orderid;
    private String product_id;
    private String appraise;// （评价基值，建议：好评=1，中评=-1，差评=-3）
    private String contribution;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }
}
