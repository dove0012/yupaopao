package com.example.dove0012.yupaopao.utils;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by liuzl on 2016/3/24.
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    public static <T>T fromJsonString(String Json , Class<? extends T> clazz) {
        try {
            T t = (T)clazz.getGenericInterfaces();
            t = gson.fromJson(Json, clazz);
            return t;
        } catch (Exception e) {
            LogUtils.i("String2Json Error:" + e);
            return null;
        }
    }

    public static String toJsonString(Class<?> clazz) {
        return gson.toJson(clazz);
    }

    public static JSONObject toJsonObject(String jsonString) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            ToastUtils.show("解析JSON错误!");
        }
        return json;
    }

    public static ArrayList<String> toStringList(String JsonString, String key) {
        JSONArray jsonArray = toJsonArray(JsonString);
        if (null != jsonArray) {
            ArrayList<String> arrayList = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(jsonArray.optJSONObject(i).optString(key));
            }
            return arrayList;
        }
        return null;
    }

    public static JSONArray toJsonArray(String jsonString) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            ToastUtils.show("解析JSON错误!");
        }
        return jsonArray;
    }
}