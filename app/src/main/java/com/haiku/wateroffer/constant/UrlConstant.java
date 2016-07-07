package com.haiku.wateroffer.constant;

/**
 * Url常量
 * Created by hyming on 2016/7/7.
 */
public class UrlConstant {
    private final static String IP = "http://";// IP地址
    private final static String PORT = "";//端口
    private final static String HOST = IP + "/" + PORT;


    private final static String PATHS_USER = "/user";

    /**
     * 用户相关path
     */
    private static String getUserPath() {
        return HOST + PATHS_USER;
    }

    /**
     * 用户相关url
     */
    public static class User {
        /**
         * 登陆url
         */
        public static String getLoginUrl() {
            return getUserPath() + "/login";
        }
    }

}
