package com.haiku.wateroffer.common.util.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.haiku.wateroffer.App;

/**
 * Created by hyming on 2016/8/1.
 */
public class SharedPreferencesUtils {

    private final static String APP_PREFERENCES = "app_preferences";
    public final static String USER = "user";

    private static SharedPreferences getPreferences() {
        return App.getInstance().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void save(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String load(String key) {
        return getPreferences().getString(key, "");
    }
}
