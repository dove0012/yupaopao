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
    private HttpResponeBean httpResponeBean;
    private httpSubscriber subscriber = null;

    public void getRespone(String url, httpSubscriber<HttpResponeBean> subscriber) {
        getRespone(url, null, subscriber);
    }

    public void getRespone(String url, RequestParams params, httpSubscriber<HttpResponeBean> subscriber) {
        //要是之前已经订阅事件,先取消之前的订阅
        unsubscribe();
        this.subscriber = subscriber;

        HttpResponeModel.getInstance().getRespone(url, params)
                .subscribeOn(Schedulers.io())                                 // 在非UI线程中执行getRespone
                .observeOn(AndroidSchedulers.mainThread())                   // 在UI线程中执行结果
                .subscribe(subscriber);
    }

    public void unsubscribe(){
        if (subscriber != null && subscriber.isUnsubscribed()) {
            LogUtils.i("------------toUnsubscribe");
            subscriber.unsubscribe();
        }
    }

    class httpSubscriber<HttpResponeBean> extends Subscriber{
        @Override
        public void onCompleted() {
            mOnCompleted();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.show(e.getMessage());
        }

        @Override
        public void onNext(Object o) {
            com.example.dove0012.yupaopao.bean.HttpResponeBean respone = (com.example.dove0012.yupaopao.bean.HttpResponeBean)o;
            if (1 == respone.getStatus()) {
                httpResponeBean = respone;
                mOnNext(respone);
            } else {
                ToastUtils.show(respone.getInfo());
            }
        }

        public void mOnNext(com.example.dove0012.yupaopao.bean.HttpResponeBean respone) {}

        public void mOnCompleted() {}
    }
}