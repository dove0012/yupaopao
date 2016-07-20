package com.example.dove0012.yupaopao.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dove0012.yupaopao.interfaces.Ifragment;
import com.example.dove0012.yupaopao.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by dove0012 on 15/10/11.
 */
public abstract class BaseFragment extends Fragment implements Ifragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setRootView(), null);
        ButterKnife.bind(this, view);
        initWidget();
        LogUtils.i(this.getClass().getName() + ":onCreateView------------onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(this.getClass().getName() + ":onStart------------onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(this.getClass().getName() + ":onResume------------onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(this.getClass().getName() + ":onPause------------onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(this.getClass().getName() + ":onStop------------onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(this.getClass().getName() + ":onDestroy------------onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mDestroy();
        LogUtils.i(this.getClass().getName() + ":onDestroyView------------onDestroyView");
    }

    public void initWidget(){ }

    public void mDestroy() { }
}