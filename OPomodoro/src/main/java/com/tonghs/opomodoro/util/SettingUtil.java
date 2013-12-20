package com.tonghs.opomodoro.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 13-12-20.
 */
public class SettingUtil {
    public final static String SETTINGS = "settings";
    public final static String BTN_SOUND = "btn_sound";
    public final static String RING_AT_END = "ring_at_end";
    public final static String VIBRATE_AT_END = "vibrate_at_end";
    public final static String CLOCK_TICK = "clock_tick";

    public static void setSetting(Context context, String name, boolean b){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(name, b);
        editor.commit();
    }

    public static boolean getSetting(Context context, String name){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, 0);
        boolean b = settings.getBoolean(name, false);

        return b;
    }
}
