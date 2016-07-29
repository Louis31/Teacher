package com.haiku.wateroffer.common;

import com.haiku.wateroffer.bean.AccessToken;
import com.haiku.wateroffer.bean.User;

/**
 * 用户信息管理类
 * Created by hyming on 2016/7/13.
 */
public class UserManager {
    private static UserManager instance = null;

    private AccessToken token;
    private User user;
    private boolean isPayDeposit;// 是否缴纳保证金
    private int range;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPayDeposit() {
        return isPayDeposit;
    }

    public void setIsPayDeposit(boolean isPayDeposit) {
        this.isPayDeposit = isPayDeposit;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    // 判断token是否为空
    public static boolean isTokenEmpty() {
        if (null == getInstance().getToken()
                || getInstance().getToken().getAccess_token().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static void cleanToken() {
        if (null != getInstance().getToken()) {
            getInstance().getToken().setAccess_token("");
        }
    }
}
