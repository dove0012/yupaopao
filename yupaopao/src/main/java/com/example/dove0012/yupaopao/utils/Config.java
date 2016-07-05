package com.example.dove0012.yupaopao.utils;

import android.content.Context;

/**
 * Created by dove0012 on 15/9/20.
 */
public class Config {
    public static final String appName = "yupaopao";
    public static final double VERSION = 1.00; //APP版本
    public static final boolean isDebug = true;

    //preferences config
    public static final String PfAppConfig = "AppConfig";
    public static final String isStart = "isStart";
    public static final String isLogin = "isLogin";
    public static final String sessionStr = "sessionId";

    //public  static  final String serverName = "http://192.168.1.108";
    public static final String serverName = "http://192.168.204.253";
    public static final String requestUri = "/Home/"; //定义服务器地址
    public static final String httpHost = serverName + requestUri; //定义服务器地址
    public static Context context = null; //上下文
    public static String sessionId = null; //服务器session_id

    public static final int hourWheelView = 1;
    public static final int shopAdressWheelView = 2;
    public static final int datepicker = 3;
    public static final int gamesWheelView = 4;
}