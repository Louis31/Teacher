package com.haiku.wateroffer.bean;

import com.google.gson.JsonElement;
import com.haiku.wateroffer.common.util.data.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * 数据返回结果bean
 * Created by hyming on 2016/7/7.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class ResultData implements Serializable {
    private int retcode;
    private JsonElement retmsg;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public JsonElement getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(JsonElement retmsg) {
        this.retmsg = retmsg;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "retcode=" + retcode +
                ", retmsg=" + retmsg +
                '}';
    }
}
