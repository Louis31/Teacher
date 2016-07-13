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

    private final static String PATHS_USER = "/user";

    /**
     * 用户相关path
     */
    private static String getUserPath() {
        return HOST + PATHS_USER;
    }

    /**
     * 获取授权token
     */
    public static String tokenUrl() {
        return HOST + "/auth/accesstoken/" + APP_SECRET;
    }

    private static String getToken() {
        return "?token=" + UserManager.getInstance().getToken().getAccess_token();
    }

    /**
     * 获取短信验证码
     */
    public static String smsCodeUrl() {
        return HOST + "/sms/send" + getToken();
    }

    /**
     * 用户相关url
     */
    public static class User {
        // 登陆url
        public static String loginUrl() {
            return getUserPath() + "/identify" + getToken();
        }
    }
}
