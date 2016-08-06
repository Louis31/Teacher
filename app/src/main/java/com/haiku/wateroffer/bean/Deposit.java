package com.haiku.wateroffer.bean;

/**
 * Created by hyming on 2016/8/4.
 */
public class Deposit {
    private String serial;// 支付序列号
    private String payment_package;// 支付签名包

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPayment_package() {
        return payment_package;
    }

    public void setPayment_package(String payment_package) {
        this.payment_package = payment_package;
    }
}
