package com.example.dove0012.yupaopao.interfaces;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;

/**
 * Created by liuzl on 2016/3/28.
 */
public interface LoginView {
    public void loginStart();
    public void loginFinish();
    public void loginSucceed(HttpResponeBean respone);
}