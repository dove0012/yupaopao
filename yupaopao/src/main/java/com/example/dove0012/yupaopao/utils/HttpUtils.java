package com.example.dove0012.yupaopao.utils;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by liuzl on 2015/9/17.
 * 自定义volley的http请求类
 */
public class HttpUtils {
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();    //实例同步对象

    static {
        syncHttpClient.setTimeout(6000);   //设置链接超时，如果不设置，默认为10s
    }

    //智能判断URL链接
    public static String packUrl(String url) {
        if (url.equals("")) return url;
        url = "/".equals(url.charAt(0)) ? url.substring(1) : url;
        url = "http".equals(url.substring(0, 4)) ? url : Config.httpHost + url;
        LogUtils.i("HttpUtils:URL------------" + url);
        return url;
    }

    //不带参数，get方式获取json对象或者数组
    public static void get(String url,TextHttpResponseHandler res) {
        if (Config.sessionId != null) {
            RequestParams params = new RequestParams();
            params.put("session_id", Config.sessionId);
            LogUtils.i("HttpUtils:params------------" + params);
            syncHttpClient.get(packUrl(url), params, res);
        } else {
            LogUtils.i("HttpUtils:params------------NULL");
            syncHttpClient.get(packUrl(url), res);
        }
    }

    //不带参数，post方式获取json对象或者数组
    public static void post(String url,RequestParams params,TextHttpResponseHandler res) {
        if (Config.sessionId != null) params.put("session_id", Config.sessionId);
        LogUtils.i("HttpUtils:params------------" + params);
        syncHttpClient.post(packUrl(url), params, res);
    }
}