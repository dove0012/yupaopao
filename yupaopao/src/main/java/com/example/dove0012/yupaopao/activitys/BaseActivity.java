package com.example.dove0012.yupaopao.activitys;

import android.app.Activity;
import android.os.Bundle;
import com.example.dove0012.yupaopao.interfaces.Iactivity;
import com.example.dove0012.yupaopao.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by dove0012 on 15/8/15.
 */
public abstract class BaseActivity extends Activity implements Iactivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(this.getClass().getName() + ":onCreate------------onCreate");
        setRootView();
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i(this.getClass().getName() + ":onStart------------onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i(this.getClass().getName() + ":onResume------------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i(this.getClass().getName() + ":onPause------------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i(this.getClass().getName() + ":onStop------------onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mDestroy();
        LogUtils.i(this.getClass().getName() + ":onDestroy------------onDestroy");
    }

    public void initWidget() { }

    public void mDestroy() { }
}