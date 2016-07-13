package com.haiku.wateroffer.common.util.data;

import com.haiku.wateroffer.common.UserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数工具类
 * Created by hyming on 2016/7/7.
 */
public class ParamUtils {

    /**
     * 获取短信验证码参数 Get
     */
    public static Map<String, Object> getSmsCodeParams(String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        return map;
    }

    /**
     * 用户相关参数
     */
    public static class User {
        // 获取登陆参数
        public static Map<String, Object> getLoginParams(String phone,String valicode) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("phone", phone);
            map.put("valicode", valicode);
            return map;
        }
    }
}
