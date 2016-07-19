package com.haiku.wateroffer.common.util.data;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyming on 2016/7/18.
 */
public class StringUtils {

    // 获取格式化当前时间（2016年01月23日）
    public static String formatDate(int year, int month, int day) {
        String monthStr = month + "";
        String dateStr = day + "";
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        if (day < 10) {
            dateStr = "0" + dateStr;
        }
        return year + "年" + monthStr + "月" + dateStr + "日";
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    // 解析error.xml文件
    public static Map<Integer, String> parserErrorXml(XmlResourceParser parser) throws Exception {
        final String TAG_ERROR = "error";
        Map<Integer, String> errors = new HashMap<>();
        int eventType = parser.getEventType();//得到第一个事件类型
        while (eventType != XmlPullParser.END_DOCUMENT) {//如果事件类型不是文档结束的话则不断处理事件
            switch (eventType) {
                case (XmlPullParser.START_DOCUMENT)://如果是文档开始事件
                    break;
                case (XmlPullParser.START_TAG)://如果遇到标签开始
                    String tagName = parser.getName();// 获得解析器当前元素的名称
                    if (TAG_ERROR.equals(tagName)) {//如果当前标签名称是<person>
                        int code = new Integer(parser.getAttributeValue(0));
                        String value = parser.nextText();
                        errors.put(code, value);
                    }
                    break;
                case (XmlPullParser.TEXT)://如果遇到标签内容
                    break;
                case (XmlPullParser.END_TAG)://如果遇到标签结束
                    break;
            }
            eventType = parser.next();//进入下一个事件处理
        }
        return errors;
    }
}
