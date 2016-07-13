package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * Created by hyming on 2016/7/13.
 */
public class AccessToken implements Serializable {
    private String access_token;
    private String expire_at;
    private String expire_at2;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
    }

    public String getExpire_at2() {
        return expire_at2;
    }

    public void setExpire_at2(String expire_at2) {
        this.expire_at2 = expire_at2;
    }
}
