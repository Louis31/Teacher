package com.haiku.wateroffer.common;

/**
 * 订单状态类型枚举
 * Created by hyming on 2016/7/25.
 */
public enum OrderStatus {
    // all,unpay,payed,delivering,received,canceled,,closed,refunded
    // 全部,待付款,已付款,配送中,已收到,,已取消,已完成,退款中
    ALL("all", "全部"), UNPAY("unpay", "待付款"), PAYED("payed", "已付款"), DELIVERING("delivering", "配送中"), RECEIVED("received", "已收到"), CANCELED("canceled", "已取消"), CLOSED("closed", "已完成"), REFUNDED("refunded", "退款中");

    private String status;
    private String name;

    private OrderStatus(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
