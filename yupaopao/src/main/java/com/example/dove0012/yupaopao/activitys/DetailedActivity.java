package com.example.dove0012.yupaopao.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.interfaces.PhotosView;
import com.example.dove0012.yupaopao.presenter.PhotosPresenter;
import com.example.dove0012.yupaopao.ui.DividerItemDecoration;
import com.example.dove0012.yupaopao.ui.ViewHolder;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.Bind;
import butterknife.OnClick;

public class DetailedActivity extends BaseActivity implements PhotosView {

    @Bind(R.id.constellation) TextView constellationTextView;
    @Bind(R.id.age) TextView ageTextView;
    @Bind(R.id.signature) TextView signatureTextView;
    @Bind(R.id.hot) TextView hotTextView;
    @Bind(R.id.favour) TextView favourTextView;
    @Bind(R.id.gameArea) TextView gameAreaTextView;
    @Bind(R.id.gameRole) TextView gameRoleTextView;
    @Bind(R.id.gameLevel) TextView gameLevelTextView;
    @Bind(R.id.profession) TextView professionTextView;
    @Bind(R.id.company) TextView companyTextView;
    @Bind(R.id.games) TextView gamesTextView;
    @Bind(R.id.often_go) TextView often_goTextView;
    @Bind(R.id.school) TextView schoolTextView;
    @Bind(R.id.sexImage) ImageView sexImageView;
    @Bind(R.id.picture) ImageView pictureImageView;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    private JSONObject jsonObject = null;
    private JSONArray photos = null;

    private PhotosPresenter photosPresenter = new PhotosPresenter(this);

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_detailed);
    }

    @Override
    public void initWidget() {
        Bundle bundle = this.getIntent().getExtras();
        jsonObject = JsonUtils.toJsonObject(bundle.get("info").toString());
        if (null != jsonObject) {
            constellationTextView.setText(jsonObject.optString("constellation"));
            ageTextView.setText(jsonObject.optString("age"));
            hotTextView.setText(jsonObject.optString("hot"));
            favourTextView.setText(jsonObject.optString("favour"));
            signatureTextView.setText(jsonObject.optString("signature"));
            if (jsonObject.optString("sex").equals("男")) sexImageView.setImageResource(R.mipmap.small_sex_boy);
            gameAreaTextView.setText(jsonObject.optString("game_area"));
            gameRoleTextView.setText(jsonObject.optString("game_role"));
            gameLevelTextView.setText(jsonObject.optString("game_level"));
            professionTextView.setText(jsonObject.optString("profession"));
            companyTextView.setText(jsonObject.optString("company"));
            schoolTextView.setText(jsonObject.optString("school"));
            String pictureStr = jsonObject.optString("picture");
            if (BasicUtils.judgeNotNull(pictureStr)) Glide.with(this).load(Config.serverName + pictureStr).into(pictureImageView);
            String temp;
            String gamesStr = jsonObject.optString("games");
            if (BasicUtils.judgeNotNull(gamesStr)) {
                temp = gamesStr.replaceAll("\\|", "\n");
                gamesTextView.setText(temp);
            }
            String often_goStr = jsonObject.optString("often_go");
            if (BasicUtils.judgeNotNull(often_goStr)) {
                temp = often_goStr.replaceAll("\\|", "\n");
                often_goTextView.setText(temp);
            }
            photosPresenter.takePhotos(jsonObject.optInt("member_id"));
        }
    }

    @OnClick({ R.id.to_date})
    public void click(View v) {
        switch (v.getId()){
            case R.id.to_date:
                BasicUtils.sendIntent(this,ToDateActivity.class, "info", jsonObject.toString());
                break;
        }
    }

    @Override
    public void photosSucceed(String response) {
        photos = JsonUtils.toJsonArray(response);
        if (photos != null && photos.length() > 0) {
            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            //设置适配器
            recyclerView.setAdapter(new MyAdapter());
            //添加分割线
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.mViewHolder> {

        @Override
        public MyAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(DetailedActivity.this, R.layout.activity_detailed_list_item, null);
            // 创建一个ViewHolder
            mViewHolder holder = new mViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.mViewHolder holder, int position) {
            String picture = photos.optJSONObject(position).optString("path");
            if (BasicUtils.judgeNotNull(picture)) Glide.with(DetailedActivity.this).load(Config.serverName + picture).into(holder.picture);
        }

        @Override
        public int getItemCount() {
            return photos.length();
        }

        public class mViewHolder extends ViewHolder {
            @Bind(R.id.picture) ImageView picture;
            public mViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BasicUtils.sendIntent(DetailedActivity.this,PhotoActivity.class, "info", photos.optJSONObject(getAdapterPosition()).toString());
                    }
                });
            }
        }
    }
}