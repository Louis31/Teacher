package com.haiku.wateroffer.constant;

/**
 * Created by hyming on 2016/7/12.
 */
public class BaseConstant {
    // 网络返回的错误代码常量
    public final static int SUCCESS = 0;// 返回成功
    public final static int SERVER_ERROR = 998;// 服务器错误
    public final static int METWORD_ERROR = 999;// 网络错误

    //
    public final static int DEVICE_TYPE = 1;// 设备类型：0为IOS，1为Android
    public final static int PAGE_SIZE_DEFAULT = 20;// 默认每页显示数量

    // 登录角色类型 商家端 ，客户端 ，配送员端 'merchant','buyer','diliveryman'
    public final static String LOGIN_TYPE_MERCHANT = "merchant";
    public final static String LOGIN_TYPE_BUYER = "buyer";
    public final static String LOGIN_TYPE_DILIVERYMAN = "diliveryman";

    // 订单筛选条件 综合：synthesis 销量：sales 价格：price
    public final static String ORDER_SORT_SYNTHESIS = "synthesis";
    public final static String ORDER_SORT_SALES = "sales";
    public final static String ORDER_SORT_PRICE = "price";
}
