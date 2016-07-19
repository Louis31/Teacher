package com.haiku.wateroffer.common.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by hyming on 2016/7/9.
 */
public class GsonUtils {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtils() {
    }


    /**
     * Object对象转 json字符串
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String str = null;
        if (gson != null) {
            str = gson.toJson(object);
        }
        return str;
    }

    /**
     * 将json字符串解析为：cls类型的对象
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null && gsonString != null && !gsonString.equals("")) {
            try {
                t = gson.fromJson(gsonString, cls);
            } catch (Exception e) {
                System.out.println(cls.getSimpleName() + "  " + e.toString());
            }

        }
        return t;
    }

    /**
     * 将json字符串，转成：T类对象的 List数组
     *
     * @param gsonString
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString,Class clazz) {
        List<T> list = null;
        if (gson != null) {
            Type objectType = type(List.class, clazz);
            list = gson.fromJson(gsonString, objectType);
        }
        return list;
    }

    // 判断json数组是否为空
    public static boolean isJsonArrayEmpty(JsonArray jArray) {
        if (jArray == null || jArray.size() == 0 || jArray.toString().equals("[]"))
            return true;
        return false;
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
