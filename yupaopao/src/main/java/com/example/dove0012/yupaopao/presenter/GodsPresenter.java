package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.GodsView;

/**
 * Created by liuzl on 2016/3/28.
 */
public class GodsPresenter extends HttpResponePresenter {
    private GodsView godsView;

    public GodsPresenter(GodsView godsView) {
        this.godsView = godsView;
    }

    public void takeGods() {
        this.getRespone("God/take/", new httpSubscriber<HttpResponeBean>() {
            @Override
            public void mOnNext(HttpResponeBean respone) {
                godsView.GodsSucceed(respone);
            }
        });
    }
}