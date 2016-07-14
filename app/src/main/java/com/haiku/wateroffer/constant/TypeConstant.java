package com.haiku.wateroffer.constant;

/**
 * 类型常量类
 * Created by hyming on 2016/7/11.
 */
public class TypeConstant {
    // all,unpay,payed,received,canceled,closed,refunded,delivering
    public static class Order {
        public final static String ALL = "all";// 全部
        public final static String UNPAY = "unpay";// 待付款
        public final static String PAYED = "payed";// 已付款
        public final static String RECEIVED = "received";// 已收到
        public final static String CANCELED = "canceled";// 已取消
        public final static String CLOSED = "closed";// 已完成
        public final static String REFUNDED = "refunded";// 退款中
        public final static String DELIVERING = "delivering";// 配送中

    }

    public static class Goods {
        public final static int OFF_SHELF = 0;// 已下架
        public final static int ON_SALE = 1;// 出售中
        public final static int SALE_NONE = 2;// 已售罄
    }
}
