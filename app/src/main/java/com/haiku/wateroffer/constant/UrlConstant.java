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
    private final static String PATH_DELIVER = "/product";
    private final static String PATH_SHOP = "/shops";

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

    // 配送相关Path
    private static String getDeliverPath() {
        return HOST + PATH_DELIVER;
    }

    // 店铺相关
    private static String getShopPath() {
        return HOST + PATH_SHOP;
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

        // 修改商家电话，Post
        public static String changePhone() {
            return getUserPath() + "/changeLoginPhone" + getToken();
        }

        // 修改店铺logo，Post
        public static String changeShopLogo() {
            return getUserPath() + "/modifyShopLogo" + getToken();
        }

        // 修改店铺地址，Post
        public static String addShopAddress() {
            return getUserPath() + "/addShopAddress" + getToken();
        }

        // 获取账单明细，Get
        public static String getBillList() {
            return getUserPath() + "/getbillsList" + getToken();
        }

        // 获取查询账单，Get
        public static String searchBill() {
            return getUserPath() + "/requestBillDetail" + getToken();
        }

        // 获取配送员列表，Get
        public static String getDeliverList() {
            return getUserPath() + "/requestDiliverymanList" + getToken();
        }

        // 编辑配送员，Get
        public static String editDeliver() {
            return getUserPath() + "/editDiliveryman" + getToken();
        }

        // 添加配送员，Get
        public static String addDeliver() {
            return getUserPath() + "/addDiliveryman" + getToken();
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

        // 获取订单详情，Get
        public static String infoUrl() {
            return getOrderPath() + "/get_info" + getToken();
        }

        // 取消配送，Post
        public static String cancelUrl() {
            return getOrderPath() + "/cancel_delivery" + getToken();
        }

        // 派单，Post
        public static String sendUrl() {
            return getOrderPath() + "/send_orders" + getToken();
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

        // 删除商品，Get
        public static String deleteUrl() {
            return getGoodsPath() + "/deleteProduct" + getToken();
        }

        // 下架商品
        public static String offShelfUrl() {
            return getGoodsPath() + "/saleOff" + getToken();
        }
    }

    /**
     * 配送员相关
     */
    public static class Deliver {
        // 获取配送员订单列表
        public static String getOrderList() {
            return getDeliverPath() + "/get_order" + getToken();
        }
    }

    /**
     * 店铺相关
     */
    public static class Shop {
        // 获取店铺信息，Get
        public static String shopInfoUrl() {
            return getOrderPath() + "/get_shop_info" + getToken();
        }

        // 修改店铺名称，Post
        public static String changeShopNameUrl() {
            return getShopPath() + "/changeShopName" + getToken();
        }

        // 修改店铺联系电话，Post
        public static String changeShopPhoneUrl() {
            return getShopPath() + "/changeShopPhone" + getToken();
        }

        // 获取店铺QQ，Get
        public static String getShopQQUrl() {
            return getShopPath() + "/getShopQQ" + getToken();
        }

        // 修改店铺QQ，Post
        public static String changeShopQQUrl() {
            return getShopPath() + "/changeShopQQ" + getToken();
        }
    }
}
