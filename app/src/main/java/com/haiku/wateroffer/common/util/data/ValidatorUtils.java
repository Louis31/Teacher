package com.haiku.wateroffer.common.util.data;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by hyming on 2016/7/13.
 */
public class ValidatorUtils {
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "1[34578]\\d{9}$";


    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

}
