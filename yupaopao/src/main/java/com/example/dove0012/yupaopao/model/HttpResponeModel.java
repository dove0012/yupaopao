package com.example.dove0012.yupaopao.model;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.utils.HttpUtils;
import com.example.dove0012.yupaopao.utils.JsonUtils;
import com.example.dove0012.yupaopao.utils.LogUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by liuzl on 2016/3/24.
 */
public class HttpResponeModel {
    private static HttpResponeModel instance = null;

    public static HttpResponeModel getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new HttpResponeModel();
        }
    }

    private HttpResponeModel() {}

    public Observable<HttpResponeBean> getRespone(final String url, final RequestParams params) {
        return Observable.create(new Observable.OnSubscribe<HttpResponeBean>() {
            @Override
            public void call(final Subscriber<? super HttpResponeBean> subscriber) {
                TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        LogUtils.i("Error statusCode:" + statusCode);
                        LogUtils.i("Error HttpResponse:" + responseString);
                        subscriber.onError(new Exception("服务器错误!"));
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        LogUtils.i("HttpResponse ------------ :" + responseString);
                        HttpResponeBean response = new HttpResponeBean();
                        JSONObject jsonObject = JsonUtils.toJsonObject(responseString);
                        response.setInfo(jsonObject.optString("info"));
                        response.setStatus(jsonObject.optInt("status"));
                        response.setUrl(jsonObject.optString("url"));
                        subscriber.onNext(response);
                    }

                    @Override
                    public void onFinish() {
                        LogUtils.i("onFinish:");
                        subscriber.onCompleted();
                    }
                };

                if (null == params) {
                    HttpUtils.get(url, textHttpResponseHandler);
                } else {
                    HttpUtils.post(url, params, textHttpResponseHandler);
                }
            }
        });
    }
}