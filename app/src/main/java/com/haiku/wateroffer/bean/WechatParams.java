package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 用于解析微信支付所需参数列表的类
 * Created by hyming on 2016/7/27.
 */
public class WechatParams implements Serializable {
    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String noncestr;
    private String packagevalue;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackagevalue() {
        return packagevalue;
    }

    public void setPackagevalue(String packagevalue) {
        this.packagevalue = packagevalue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WechatParams{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packagevalue='" + packagevalue + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
