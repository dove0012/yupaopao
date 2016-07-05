package com.example.dove0012.yupaopao.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.LoginView;
import com.example.dove0012.yupaopao.presenter.LoginPresenter;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.PreferencesUtils;
import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.user_edit) EditText userPhoneEditText;
    @Bind(R.id.passwd_edit) EditText passWordEditText;
    @Bind(R.id.forget_passwd) Button forgetPasswdBtn;

    public static LoginActivity instance;
    private ProgressDialog progressDialog;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initWidget() {
        instance = this;
    }

    @OnClick({ R.id.register_btn,R.id.login_btn})
    public void click(View v) {
        switch (v.getId()){
            case R.id.register_btn: //注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_btn: //登陆
                LoginPresenter.getInstance(this).toLogin(userPhoneEditText.getText().toString(), passWordEditText.getText().toString());
                break;
        }
    }

    @Override
    public void loginStart() {
        //加载滚动条提示登陆中
        progressDialog = ProgressDialog.show(LoginActivity.this, "登陆中...", "请稍后!", true, false);
    }

    @Override
    public void loginFinish() {
        //请求完成,去掉滚动条
        progressDialog.dismiss();
    }

    @Override
    public void loginSucceed(HttpResponeBean respone) {
        PreferencesUtils.write(Config.PfAppConfig, Config.isLogin, true);
        Config.sessionId = respone.getInfo();
        PreferencesUtils.write(Config.PfAppConfig, Config.sessionStr, Config.sessionId);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        instance.finish();
    }
}