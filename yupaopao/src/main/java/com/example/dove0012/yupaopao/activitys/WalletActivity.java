package com.example.dove0012.yupaopao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.dove0012.yupaopao.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_wallet);
    }

    @OnClick({ R.id.available_balance_item})
    public void click(View v) {
        switch (v.getId()){
            case R.id.available_balance_item:
                startActivity(new Intent(WalletActivity.this, RechargeActivity.class));
                break;
        }
    }
}
