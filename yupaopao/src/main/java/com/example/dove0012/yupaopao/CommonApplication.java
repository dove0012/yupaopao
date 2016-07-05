package com.example.dove0012.yupaopao;

import android.app.Application;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.PreferencesUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * CommonApplication which config ImageLoader and ActiveAndroid
 */
public class CommonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);

        Config.context = getApplicationContext();
        String sessionId = PreferencesUtils.readString(Config.PfAppConfig, Config.sessionStr);
        if (sessionId != null) Config.sessionId = sessionId;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
