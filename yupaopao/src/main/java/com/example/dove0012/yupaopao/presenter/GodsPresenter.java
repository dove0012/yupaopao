package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.GodsView;

/**
 * Created by liuzl on 2016/3/28.
 */
public class GodsPresenter extends HttpResponePresenter {
    private GodsView godsView;
    private static GodsPresenter instance = null;

    private static synchronized void syncInit(GodsView godsView) {
        if (instance == null) {
            instance = new GodsPresenter(godsView);
        }
    }

    public static GodsPresenter getInstance(GodsView godsView) {
        if (instance == null) {
            syncInit(godsView);
        }
        instance.godsView = godsView;
        return instance;
    }

    private GodsPresenter(GodsView godsView) {
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