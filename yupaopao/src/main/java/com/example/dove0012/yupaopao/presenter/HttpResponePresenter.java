package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.model.HttpResponeModel;
import com.example.dove0012.yupaopao.utils.LogUtils;
import com.example.dove0012.yupaopao.utils.ToastUtils;
import com.loopj.android.http.RequestParams;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liuzl on 2016/3/25.
 */
public abstract class HttpResponePresenter {
    private boolean loading = false;
    private HttpResponeBean httpResponeBean;
    private long createTime;
    private httpSubscriber subscriber = null;

    public void getRespone(String url, httpSubscriber<HttpResponeBean> subscriber) {
        getRespone(url, null, 6000, subscriber);
    }

    public void getRespone(String url, RequestParams params, httpSubscriber<HttpResponeBean> subscriber) {
        getRespone(url, params, 6000, subscriber);
    }

    public void getRespone(String url, RequestParams params, long cacheTime, httpSubscriber<HttpResponeBean> subscriber) {
        this.subscriber = subscriber;
        if (loading) {
            ToastUtils.show("数据加载中!");
            return;
        }
        //判断缓存
        long time = System.currentTimeMillis() - createTime;
        if (httpResponeBean != null && time < cacheTime) {
            subscriber.mOnNext(httpResponeBean);
            return;
        }
        loading = true;
        HttpResponeModel.getInstance().getRespone(url, params)
                .subscribeOn(Schedulers.io())                                 // 在非UI线程中执行getRespone
                .observeOn(AndroidSchedulers.mainThread())                   // 在UI线程中执行结果
                .subscribe(subscriber);
    }

    public void unsubscribe(){
        LogUtils.i("------------toUnsubscribe");
        if (subscriber != null && subscriber.isUnsubscribed())
            subscriber.unsubscribe();
    }

    class httpSubscriber<HttpResponeBean> extends Subscriber{
        @Override
        public void onCompleted() {
            loading = false;
            mOnCompleted();
        }

        @Override
        public void onError(Throwable e) {
            loading = false;
            ToastUtils.show(e.getMessage());
        }

        @Override
        public void onNext(Object o) {
            com.example.dove0012.yupaopao.bean.HttpResponeBean respone = (com.example.dove0012.yupaopao.bean.HttpResponeBean)o;
            if (1 == respone.getStatus()) {
                httpResponeBean = respone;
                createTime = System.currentTimeMillis();
                mOnNext(respone);
            } else {
                ToastUtils.show(respone.getInfo());
            }
        }

        public void mOnNext(com.example.dove0012.yupaopao.bean.HttpResponeBean respone) {}

        public void mOnCompleted() {}
    }
}