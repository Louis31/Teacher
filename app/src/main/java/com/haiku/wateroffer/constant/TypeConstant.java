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

    public static class OrderOpera {
        public final static int SEND_ORDER = 0;// 派单
        public final static int CANCEL_DELIVER = 1;// 取消配送
    }

    public static class Goods {
        public final static int OFF_SHELF = 0;// 已下架
        public final static int ON_SALE = 1;// 出售中
        public final static int SALE_NONE = 2;// 已售罄
    }

    public static class GoodsOpera {
        public final static int DELETE_GOODS = 0;// 删除商品
        public final static int OFF_SHELF = 1;// 下架商品
        public final static int UP_SHELF = 2;// 上架商品
    }

    public static class Deliver {
        public final static int DELETE = 0;// 未添加和删除状态
        public final static int PAUSE = 1;// 暂停状态
        public final static int CONTINUE = 2;// 继续状态
    }
}
