package com.haiku.wateroffer.common.util.data;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.List;

public class JsonResponseParser implements ResponseParser {
    //检查服务器返回的响应头信息
    @Override
    public void checkResponse(UriRequest request) throws Throwable {
    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        /*if (resultClass == List.class) {
            // list to json
            return GsonUtils.gsonToList(result);
        } else {
            // bean to json
            return GsonUtils.gsonToBean(result, resultClass);
        }*/
        return GsonUtils.gsonToBean(result, resultClass);
    }
}
