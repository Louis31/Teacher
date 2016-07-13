package com.haiku.wateroffer.common.util.net;

import com.haiku.wateroffer.common.util.data.LogUtils;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by hyming on 2016/7/7.
 */
public class XUtils {
    private final static int TIMEOUT = 1000 * 10;// 链接超时时间

    /**
     * 发送get请求
     *
     * @param <T>
     */
    public static <T> Cancelable Get(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(TIMEOUT);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue() + "");
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        LogUtils.showLogE("Get", params.toString());
        return cancelable;
    }

    /**
     * 发送post请求
     *
     * @param <T>
     */
    public static <T> Cancelable Post(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(TIMEOUT);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().post(params, callback);
        LogUtils.showLogE("Post", params.toString());
        return cancelable;
    }
}