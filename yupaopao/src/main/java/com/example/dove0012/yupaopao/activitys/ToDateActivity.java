package com.example.dove0012.yupaopao.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.interfaces.GamesView;
import com.example.dove0012.yupaopao.interfaces.OrderView;
import com.example.dove0012.yupaopao.interfaces.ShopAdressView;
import com.example.dove0012.yupaopao.presenter.GamesPresenter;
import com.example.dove0012.yupaopao.presenter.OrderPresenter;
import com.example.dove0012.yupaopao.presenter.ShopAddressPresenter;
import com.example.dove0012.yupaopao.utils.BasicUtils;
import com.example.dove0012.yupaopao.utils.Config;
import com.example.dove0012.yupaopao.utils.JsonUtils;
import com.example.dove0012.yupaopao.utils.LogUtils;
import com.example.dove0012.yupaopao.utils.ToastUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class ToDateActivity extends BaseActivity implements ShopAdressView,OrderView,GamesView {

    @Bind(R.id.picture) ImageView pictureImageView;
    @Bind(R.id.name) TextView nameTextView;
    @Bind(R.id.order_hour) TextView orderHourTextView;
    @Bind(R.id.order_city) TextView orderCityTextView;
    @Bind(R.id.play_time) TextView playTimeTextView;
    @Bind(R.id.play_game) TextView playGameTextView;
    @Bind(R.id.cost) TextView costTextView;
    @Bind(R.id.pay_money) TextView payMoneyTextView;
    @Bind(R.id.remark) TextView remarkTextView;

    private String shopAdress[] = null;
    private String games[] = null;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject = null;
    private String shopAdressJson = null;
    private String gamesJson = null;

    private int hour = 1;
    private int shopId = 0;
    private int gameId = 0;
    private String time = null;

    private AlertDialog.Builder builder = null;
    private AlertDialog alertDialog = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_to_date);
    }

    @Override
    public void initWidget() {
        Bundle bundle = getIntent().getExtras();
        jsonObject = JsonUtils.toJsonObject(bundle.get("info").toString());
        String pictureStr = jsonObject.optString("picture");
        if (BasicUtils.judgeNotNull(pictureStr)) Glide.with(this).load(Config.serverName + pictureStr).into(pictureImageView);
        nameTextView.setText(jsonObject.optString("name"));
        costTextView.setText(jsonObject.optString("cost") + "元/小时");
        payMoneyTextView.setText(jsonObject.optInt("cost") + "元");
    }

    @OnClick({R.id.order_city, R.id.order_hour, R.id.play_time, R.id.play_game, R.id.do_date})
    public void click(View v) {
        switch (v.getId()){
            case R.id.order_city:
                if (null == shopAdress) {
                    ShopAddressPresenter.getInstance(this).getShopAdress();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(this, SingleWheelViewActivity.class);
                    intent.putExtra("data", shopAdress);
                    startActivityForResult(intent, Config.shopAdressWheelView);
                }
                break;
            case R.id.order_hour:
                startActivityForResult(new Intent(this, HourWheelViewActivity.class), Config.hourWheelView);
                break;
            case R.id.play_time:
                startActivityForResult(new Intent(this, DatePickerActivity.class), Config.datepicker);
                break;
            case R.id.play_game:
                if (null == games) {
                    GamesPresenter.getInstance(this).getGames();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(this, SingleWheelViewActivity.class);
                    intent.putExtra("data", games);
                    startActivityForResult(intent, Config.gamesWheelView);
                }
                break;
            case R.id.do_date:
                if (0 == shopId) {
                    ToastUtils.show("请选择陪玩地点!");
                    return;
                }
                if (0 == gameId) {
                    ToastUtils.show("请选择陪玩游戏!");
                    return;
                }
                if (null == time) {
                    ToastUtils.show("请选择陪玩时间!");
                    return;
                }
                if (null == alertDialog) {
                    builder = new AlertDialog.Builder(this);
                    builder.setTitle("订单确认");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            OrderPresenter.getInstance(ToDateActivity.this).toOrder(shopId, gameId, time, hour, jsonObject.optInt("member_id"), remarkTextView.getText().toString());
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                String orderInfo = "您确定于 "+playTimeTextView.getText()+" 约 "+jsonObject.optString("name")+" 在 "+orderCityTextView.getText()+" 陪玩 "+playGameTextView.getText()+" "+hour+" 小时?";
                builder.setMessage(orderInfo);
                alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String value = bundle.getString("value");
            int position;
            JSONArray jsonArray;
            switch (requestCode) {
                case Config.hourWheelView:
                    orderHourTextView.setText(value + "小时");
                    payMoneyTextView.setText(jsonObject.optInt("cost") * Integer.parseInt(value) + "元");
                    hour = Integer.parseInt(value);
                    break;
                case Config.shopAdressWheelView:
                    position = bundle.getInt("key");
                    jsonArray = JsonUtils.toJsonArray(shopAdressJson);
                    shopId = jsonArray.optJSONObject(position).optInt("id");
                    orderCityTextView.setText(value);
                    break;
                case Config.gamesWheelView:
                    position = bundle.getInt("key");
                    jsonArray = JsonUtils.toJsonArray(gamesJson);
                    gameId = jsonArray.optJSONObject(position).optInt("id");
                    playGameTextView.setText(value);
                    break;
                case Config.datepicker:
                    time = value;
                    playTimeTextView.setText(value);
                    break;
            }
        }
    }

    @Override
    public void startShopAdress() {
        progressDialog = ProgressDialog.show(this, "数据加载中...", "请稍后!", true, false);
    }

    @Override
    public void shopAdressFinish() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void setShopAdress(String shopAdress) {
        shopAdressJson = shopAdress;
        ArrayList<String> data = JsonUtils.toStringList(shopAdress, "name");
        if (null != data) {
            Intent intent = new Intent();
            intent.setClass(this, SingleWheelViewActivity.class);
            intent.putExtra("data", data);
            startActivityForResult(intent, Config.shopAdressWheelView);
        }
    }

    @Override
    public void orderSucceed(String info) {
        ToastUtils.show("订单生成成功!");
    }

    @Override
    public void startToOrder() {
        progressDialog = ProgressDialog.show(this, "生成订单中...", "请稍后!", true, false);
    }

    @Override
    public void orderFinish() {
        progressDialog.dismiss();
    }

    @Override
    public void startGames() {
        progressDialog = ProgressDialog.show(this, "数据加载中...", "请稍后!", true, false);
    }

    @Override
    public void gamesFinish() {
        progressDialog.dismiss();
    }

    @Override
    public void setGames(String games) {
        gamesJson = games;
        ArrayList<String> data = JsonUtils.toStringList(games, "name");
        LogUtils.i(data.toString());
        if (null != data) {
            Intent intent = new Intent();
            intent.setClass(this, SingleWheelViewActivity.class);
            intent.putExtra("data", data);
            startActivityForResult(intent, Config.gamesWheelView);
        }
    }
}