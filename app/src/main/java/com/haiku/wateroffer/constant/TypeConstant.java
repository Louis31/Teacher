package com.haiku.wateroffer.constant;

/**
 * 类型常量类
 * Created by hyming on 2016/7/11.
 */
public class TypeConstant {
    public static class Order {
        public final static int ALL = 0;// 全部订单
        public final static int PENDING_PAY = 1;// 待付款订单
        public final static int PENDING_SEND = 2;// 待发货订单
        public final static int SENDING = 3;// 配送中订单
        public final static int FINISH = 4;// 已完成订单

    }

    public static class Goods {
        public final static int ON_SALE = 0;// 出售中
        public final static int SALE_NONE = 1;// 已售罄
        public final static int OFF_SHELF = 2;// 已下架
    }
}
