package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 账单实体类
 * Created by hyming on 2016/7/18.
 */
public class Bill implements Serializable {
    // 账单列表
    private String order_amount;// 订单金额
    private int wepay_method;// 付款方式（0，1为线上付款；2为线下付款）
    private String shopname;// 店铺名称
    private String serial_number;// 订单号
    private String diliverman;

    // 账单概述
    private int billsNum;// 订单数
    private String onLineAmount;// 线上付款金额
    private String offLineAmmount;// 线下付款金额

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public int getWepay_method() {
        return wepay_method;
    }

    public void setWepay_method(int wepay_method) {
        this.wepay_method = wepay_method;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public int getBillsNum() {
        return billsNum;
    }

    public void setBillsNum(int billsNum) {
        this.billsNum = billsNum;
    }

    public String getOnLineAmount() {
        return onLineAmount;
    }

    public void setOnLineAmount(String onLineAmount) {
        this.onLineAmount = onLineAmount;
    }

    public String getOffLineAmmount() {
        return offLineAmmount;
    }

    public void setOffLineAmmount(String offLineAmmount) {
        this.offLineAmmount = offLineAmmount;
    }

    public String getDiliverman() {
        return diliverman;
    }

    public void setDiliverman(String diliverman) {
        this.diliverman = diliverman;
    }
}
