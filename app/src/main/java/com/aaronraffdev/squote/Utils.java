package com.aaronraffdev.squote;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by aaronraff on 7/10/18.
 */

public class Utils {
    final static Handler UIHandler = new Handler(Looper.getMainLooper());

    public static void runOnUIThread(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
