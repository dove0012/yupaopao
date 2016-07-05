package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.RegisterView;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.ToastUtils;
import com.loopj.android.http.RequestParams;

/**
 * Created by liuzl on 2016/3/28.
 */
public class RegisterPresenter extends HttpResponePresenter {
    private RegisterView registerView;
    private static RegisterPresenter instance = null;

    private static synchronized void syncInit(RegisterView registerView) {
        if (instance == null) {
            instance = new RegisterPresenter(registerView);
        }
    }

    public static RegisterPresenter getInstance(RegisterView registerView) {
        if (instance == null) {
            syncInit(registerView);
        }
        instance.registerView = registerView;
        return instance;
    }

    private RegisterPresenter(RegisterView registerView){
        this.registerView = registerView;
    }

    public void register(String username, String password, String verify){
        if (null != Config.sessionId) {
            if (!BasicUtils.judgeNotNull(username)) {
                ToastUtils.show("手机号码不能为空!"); return;
            }
            if (!BasicUtils.judgeNotNull(password)) {
                ToastUtils.show("密码不能为空!"); return;
            }
            if (!BasicUtils.judgeNotNull(verify)) {
                ToastUtils.show("验证码不能为空!"); return;
            }
            RequestParams params = new RequestParams();
            params.put("username", username);
            params.put("password", password);
            params.put("verify", verify);
            this.getRespone("Public/register/", params, new httpSubscriber<HttpResponeBean>() {
                @Override
                public void onStart() {
                    registerView.registerStart();
                }

                public void mOnCompleted() {
                    registerView.registerFinish();
                }

                public void mOnNext(HttpResponeBean respone) {
                    registerView.registerSucceed();
                }
            });
        } else {
            ToastUtils.show("请先等服务器加载完验证码!");
        }
    }
}