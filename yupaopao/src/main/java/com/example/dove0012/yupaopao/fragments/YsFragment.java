package com.example.dove0012.yupaopao.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.activitys.DetailedActivity;
import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.GodsView;
import com.example.dove0012.yupaopao.presenter.GodsPresenter;
import com.example.dove0012.yupaopao.ui.DividerItemDecoration;
import com.example.dove0012.yupaopao.ui.ViewHolder;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.Bind;

/**
 * Created by liuzl on 2015/6/27.
 */
public class YsFragment extends BaseFragment implements GodsView {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    private JSONArray jsonArray = null;
    private int total = 0;

    @Override
    public int setRootView() {
        return R.layout.fragment_ys;
    }

    @Override
    public void initWidget() {
        GodsPresenter.getInstance(this).takeGods();
    }

    @Override
    public void GodsSucceed(HttpResponeBean response) {
        JSONObject info = JsonUtils.toJsonObject(response.getInfo());
        if (info != null) {
            total = info.optInt("total");
            jsonArray = info.optJSONArray("rows");
            if (jsonArray != null && jsonArray.length() > 0) {
                //设置布局管理器
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                //设置适配器
                recyclerView.setAdapter(new MyAdapter());
                //添加分割线
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.mViewHolder> {

        @Override
        public MyAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.fragment_ys_list_item, null);
            // 创建一个ViewHolder
            mViewHolder holder = new mViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.mViewHolder holder, int position) {
            holder.name.setText(jsonArray.optJSONObject(position).optString("name"));
            holder.age.setText(jsonArray.optJSONObject(position).optString("age"));
            String sex = jsonArray.optJSONObject(position).optString("sex");
            if (sex.equals("男")) holder.sexImage.setImageResource(R.mipmap.small_sex_boy);
            String picture = jsonArray.optJSONObject(position).optString("picture");
            if (BasicUtils.judgeNotNull(picture)) Glide.with(YsFragment.this).load(Config.serverName + picture).into(holder.picture);
            holder.position.setText(String.valueOf(position + 1));
            holder.hot.setText(jsonArray.optJSONObject(position).optString("hot"));
        }

        @Override
        public int getItemCount() {
            return jsonArray.length();
        }

        public class mViewHolder extends ViewHolder {
            @Bind(R.id.name) TextView name;
            @Bind(R.id.age) TextView age;
            @Bind(R.id.sexImage) ImageView sexImage;
            @Bind(R.id.picture) ImageView picture;
            @Bind(R.id.position) TextView position;
            @Bind(R.id.hot) TextView hot;
            public mViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BasicUtils.sendIntent(getActivity(),DetailedActivity.class, "info", jsonArray.optJSONObject(getAdapterPosition()).toString());
                    }
                });
            }
        }
    }
}