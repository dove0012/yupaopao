package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.LoginView;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.ToastUtils;
import com.loopj.android.http.RequestParams;

/**
 * Created by liuzl on 2016/3/28.
 */
public class LoginPresenter extends HttpResponePresenter {
    private LoginView loginView;
    private LoginPresenter instance = null;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void toLogin(String username, String password) {
        if (!BasicUtils.judgeNotNull(username)) {
            ToastUtils.show("手机号码不能为空!");
            return;
        }
        if (!BasicUtils.judgeNotNull(password)) {
            ToastUtils.show("密码不能为空!");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        this.getRespone("Public/login/", params, new httpSubscriber<HttpResponeBean>() {
            @Override
            public void onStart() {
                loginView.loginStart();
            }

            @Override
            public void mOnCompleted() {
                loginView.loginFinish();
            }

            @Override
            public void mOnNext(HttpResponeBean response) {
                loginView.loginSucceed(response);
            }
        });
    }
}