package com.android.record.base.util;

import android.os.Handler;
import android.os.Looper;

/**
 *
 * Created by kiddo on 17-1-10.
 */

public class HandleUtil {
    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable runnable){
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis){
        HANDLER.postDelayed(runnable,delayMillis);
    }

    public static void removeRunable(Runnable runnable){
        HANDLER.removeCallbacks(runnable);
    }

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
