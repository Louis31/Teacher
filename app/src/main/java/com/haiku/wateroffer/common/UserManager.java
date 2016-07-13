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
}
