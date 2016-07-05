package com.example.dove0012.yupaopao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.dove0012.yupaopao.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.OnClick;

public class SingleWheelViewActivity extends BaseActivity {

    @Bind(R.id.wheelview) com.wx.wheelview.widget.WheelView wheelView;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_wheelview);
    }

    @Override
    public void initWidget() {
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> data = bundle.getStringArrayList("data");
        ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter(this);
        wheelView.setWheelAdapter(arrayWheelAdapter);
        wheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        wheelView.setWheelData(data);
    }

    @OnClick({R.id.confirm, R.id.cancel})
    public void click(View v) {
        switch (v.getId()){
            case R.id.confirm:
                Intent intent = new Intent();
                intent.putExtra("key", wheelView.getCurrentPosition());
                intent.putExtra("value", (String) wheelView.getSelectionItem());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.cancel:
                this.finish();
                break;
        }
    }
}