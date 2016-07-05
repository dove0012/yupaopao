package com.example.dove0012.yupaopao.activitys;

import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.JsonUtils;
import org.json.JSONObject;
import butterknife.Bind;

public class PhotoActivity extends BaseActivity {

    @Bind(R.id.picture) ImageView pictureImageView;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_photo);
    }

    @Override
    public void initWidget() {
        Bundle bundle = this.getIntent().getExtras();
        JSONObject jsonObject = JsonUtils.toJsonObject(bundle.get("info").toString());
        if (null != jsonObject) {
            String pictureStr = jsonObject.optString("path");
            if (BasicUtils.judgeNotNull(pictureStr)) Glide.with(this).load(Config.serverName + pictureStr).into(pictureImageView);
        }
    }
}
