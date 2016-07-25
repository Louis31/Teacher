package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 店铺信息
 * Created by hyming on 2016/7/25.
 */
public class ShopInfo implements Serializable {
    private String merchant_name;
    private String merchant_phone;
    private String merchant_shopname;
    private String merchant_logo;
    private String merchant_shoparea;
    private String merchant_shopfloorDetail;
    private String merchant_qq;

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_phone() {
        return merchant_phone;
    }

    public void setMerchant_phone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }

    public String getMerchant_shopname() {
        return merchant_shopname;
    }

    public void setMerchant_shopname(String merchant_shopname) {
        this.merchant_shopname = merchant_shopname;
    }

    public String getMerchant_logo() {
        return merchant_logo;
    }

    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
    }

    public String getMerchant_shoparea() {
        return merchant_shoparea;
    }

    public void setMerchant_shoparea(String merchant_shoparea) {
        this.merchant_shoparea = merchant_shoparea;
    }

    public String getMerchant_shopfloorDetail() {
        return merchant_shopfloorDetail;
    }

    public void setMerchant_shopfloorDetail(String merchant_shopfloorDetail) {
        this.merchant_shopfloorDetail = merchant_shopfloorDetail;
    }

    public String getMerchant_qq() {
        return merchant_qq;
    }

    public void setMerchant_qq(String merchant_qq) {
        this.merchant_qq = merchant_qq;
    }
}
