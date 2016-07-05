package com.example.dove0012.yupaopao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.activitys.WalletActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuzl on 2015/6/27.
 */
public class WdFragment extends BaseFragment {

    @Override
    public int setRootView() {
        return R.layout.fragment_wd;
    }

    @OnClick({ R.id.wallet})
    public void click(View v) {
        switch (v.getId()){
            case R.id.wallet:
                startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
        }
    }
}
