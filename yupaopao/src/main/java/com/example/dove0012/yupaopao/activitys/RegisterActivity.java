package com.example.dove0012.yupaopao.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.interfaces.RegisterView;
import com.example.dove0012.yupaopao.interfaces.SessionIdView;
import com.example.dove0012.yupaopao.presenter.RegisterPresenter;
import com.example.dove0012.yupaopao.presenter.SessionIdPresenter;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.PreferencesUtils;
import com.example.dove0012.yupaopao.utils.ToastUtils;
import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements SessionIdView, RegisterView {

    @Bind(R.id.user_edit) EditText userPhoneEditText;
    @Bind(R.id.passwd_edit) EditText passWordEditText;
    @Bind(R.id.verify_edit) EditText VerifyEditText;
    @Bind(R.id.verify_image) ImageView verifyImageView;

    public boolean isLoadingVerify = false;
    private ProgressDialog progressDialog;

    private RegisterPresenter registerPresenter = new RegisterPresenter(this);
    private SessionIdPresenter sessionIdPresenter = new SessionIdPresenter(this);

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initWidget() {
        loadVerify();
    }

    @OnClick({ R.id.reback_btn, R.id.register_btn,R.id.verify_image})
    public void click(View v) {
        switch (v.getId()){
            case R.id.reback_btn:
                this.finish();
                break;
            case R.id.register_btn:
                registerPresenter.register(userPhoneEditText.getText().toString(), passWordEditText.getText().toString(), VerifyEditText.getText().toString());
                break;
            case R.id.verify_image:
                loadVerify();
                break;
        }
    }

    private void loadVerify() {
        if (Config.sessionId == null) {
            if (isLoadingVerify == false) {
                isLoadingVerify = true;
                sessionIdPresenter.getSessionId();
            } else {
                ToastUtils.show("请求验证码中,请稍后!");
            }
        } else {
            //获取验证码
            Glide.with(this).load(Config.httpHost + "Public/verify/?session_id=" + Config.sessionId).into(verifyImageView);
        }
    }

    @Override
    public void setSessionId(String sessionId) {
        Config.sessionId = sessionId;
        Glide.with(this).load(Config.httpHost + "Public/verify/?session_id=" + Config.sessionId).into(verifyImageView);
    }

    @Override
    public void sessionIdFinish() {
        isLoadingVerify = false;
    }

    @Override
    public void registerStart() {
        //加载滚动条提示登陆中
        progressDialog = ProgressDialog.show(RegisterActivity.this, "注册中...", "请稍后!", true, false);
    }

    @Override
    public void registerFinish() {
        //请求完成,去掉滚动条
        progressDialog.dismiss();
    }

    @Override
    public void registerSucceed() {
        PreferencesUtils.write(Config.PfAppConfig, Config.sessionStr, Config.sessionId);
        LoginActivity.instance.finish();
        LoginActivity.instance = null;
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        RegisterActivity.this.finish();
    }

    @Override
    public void mDestroy() {
        registerPresenter.unsubscribe();
        sessionIdPresenter.unsubscribe();
    }
}
