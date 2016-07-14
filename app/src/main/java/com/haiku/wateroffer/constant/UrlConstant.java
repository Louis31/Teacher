package com.haiku.wateroffer.constant;

import com.haiku.wateroffer.common.UserManager;

/**
 * Url常量
 * Created by hyming on 2016/7/7.
 */
public class UrlConstant {
    // private final static String IP = "http://";// IP地址
    // private final static String PORT = "";//端口
    private final static String HOST = "http://sendwater.api.youdians.com";

    // 可用的appSecret：eUGhdvpDDpC16iUXqfMsc3ntl , 0wtcDflIXFLHx4VJ3G32cuFGg , ghrF59N53zchmGO2KsWlWuokC
    private final static String APP_SECRET = "eUGhdvpDDpC16iUXqfMsc3ntl";

    private final static String PATH_USER = "/user";
    private final static String PATH_ORDER = "/order";
    private final static String PATH_GOODS = "/product";

    // 用户相关path
    private static String getUserPath() {
        return HOST + PATH_USER;
    }

    // 订单相关path
    private static String getOrderPath() {
        return HOST + PATH_ORDER;
    }

    // 获取商品path
    private static String getGoodsPath() {
        return HOST + PATH_GOODS;
    }

    /**
     * 获取授权token
     */
    public static String tokenUrl() {
        return HOST + "/auth/accesstoken/" + APP_SECRET;
    }

    private static String getToken() {
        String token = "?token=";
        if (UserManager.getInstance().getToken() != null) {
            return token + UserManager.getInstance().getToken().getAccess_token();
        }
        return token;
    }

    /**
     * 获取短信验证码，Get
     */
    public static String smsCodeUrl() {
        return HOST + "/sms/send" + getToken();
    }

    /**
     * 用户相关url
     */
    public static class User {
        // 登陆url，Post
        public static String loginUrl() {
            return getUserPath() + "/identify" + getToken();
        }

        // 添加店铺名称，Post
        public static String addShopNameUrl() {
            return getUserPath() + "/addShopName" + getToken();
        }
    }

    /**
     * 订单相关url
     */
    public static class Order {
        // 获取订单列表，Get
        public static String listUrl() {
            return getOrderPath() + "/get_list" + getToken();
        }
    }

    /**
     * 商品相关url
     */
    public static class Goods {
        // 获取商品列表，Get
        public static String listUrl() {
            return getGoodsPath() + "/get_list" + getToken();
        }
    }
}
