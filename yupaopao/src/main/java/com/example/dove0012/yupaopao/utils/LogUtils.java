package com.example.dove0012.yupaopao.utils;

import android.util.Log;

/**
 * Created by liuzl on 2016/3/2.
 */
public class LogUtils {
    public static void i(String msg){
        Log.i(Config.appName, msg);
    }

    public static void i(String tag, String msg){
        if (Config.isDebug) Log.i(tag, msg);
    }
}
