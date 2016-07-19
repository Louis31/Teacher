package com.haiku.wateroffer.common.util.data;

import com.haiku.wateroffer.constant.BaseConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数工具类
 * Created by hyming on 2016/7/7.
 */
public class ParamUtils {

    /**
     * 用户相关参数
     */
    public static class User {
        // 获取添加店铺名称参数
        public static Map<String, Object> getAddShopNameParams(int uid, String shopName) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uid", uid);
            map.put("shopname", shopName);
            return map;
        }
    }

    /**
     * 订单相关参数
     */
    public static class Order {
        // 获取订单列表参数
        public static Map<String, Object> getListParams(Map<String, Object> params) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("login_type", BaseConstant.LOGIN_TYPE_MERCHANT);
            map.put("pagesize", BaseConstant.PAGE_SIZE_DEFAULT);
            map.put("device_type", BaseConstant.DEVICE_TYPE);
            map.putAll(params);
            return map;
        }

        // 获取订单详情
        public static Map<String, Object> getInfoParams(Map<String, Object> params) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("login_type", BaseConstant.LOGIN_TYPE_MERCHANT);
            map.put("device_type", BaseConstant.DEVICE_TYPE);
            map.putAll(params);
            return map;
        }
    }

    /**
     * 商品相关参数
     */
    public static class Goods {
        // 获取商品列表参数
        public static Map<String, Object> getListParams(Map<String, Object> params) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pagesize", BaseConstant.PAGE_SIZE_DEFAULT);
            map.putAll(params);
            return map;
        }
    }
}
