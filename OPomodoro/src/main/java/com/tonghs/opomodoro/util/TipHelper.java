package com.tonghs.opomodoro.util;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
/**
 * Created by 华帅 on 13-12-19.
 */
public class TipHelper {
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
    public static void Vibrate(final Activity activity, long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
