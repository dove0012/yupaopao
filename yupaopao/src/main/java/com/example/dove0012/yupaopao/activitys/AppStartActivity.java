package com.example.dove0012.yupaopao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.PreferencesUtils;

public class AppStartActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_app_start);
    }

    @Override
    public void initWidget() {
        Handler x = new Handler();
        x.postDelayed(new AppStartHandler(), 2000);
    }

    class AppStartHandler implements Runnable {

        public void run() {
            boolean isStart = PreferencesUtils.readBoolean(Config.PfAppConfig, Config.isStart);
            if (isStart) {
                boolean isLogin = PreferencesUtils.readBoolean(Config.PfAppConfig, Config.isLogin);
                if (isLogin) {
                    startActivity(new Intent(AppStartActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(AppStartActivity.this, LoginActivity.class));
                }
            } else {
                PreferencesUtils.write(Config.PfAppConfig, Config.isStart, true);
                startActivity(new Intent(AppStartActivity.this, IndexActivity.class));
            }
            AppStartActivity.this.finish();
        }

    }
}