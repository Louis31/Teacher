package com.haiku.wateroffer.common.util.data;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
