package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.SessionIdView;

/**
 * Created by liuzl on 2016/3/24.
 */
public class SessionIdPresenter extends HttpResponePresenter{
    private SessionIdView sessionIdView;
    private static SessionIdPresenter instance = null;

    private static synchronized void syncInit(SessionIdView sessionIdView) {
        if (instance == null) {
            instance = new SessionIdPresenter(sessionIdView);
        }
    }

    public static SessionIdPresenter getInstance(SessionIdView sessionIdView) {
        if (instance == null) {
            syncInit(sessionIdView);
        }
        instance.sessionIdView = sessionIdView;
        return instance;
    }

    private SessionIdPresenter(SessionIdView sessionIdView) {
        this.sessionIdView = sessionIdView;
    }

    public void getSessionId() {
        this.getRespone("Public/sessionid", new httpSubscriber<HttpResponeBean>() {
            public void mOnCompleted() {
                sessionIdView.sessionIdFinish();
            }

            public void mOnNext(HttpResponeBean respone) {
                sessionIdView.setSessionId(respone.getInfo());
            }
        });
    }
}
