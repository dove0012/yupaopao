package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.OrderView;
import com.loopj.android.http.RequestParams;

/**
 * Created by liuzl on 2016/4/25.
 */
public class OrderPresenter extends HttpResponePresenter {
    private OrderView orderView;

    public OrderPresenter(OrderView orderView) {
        this.orderView = orderView;
    }

    public void toOrder(int shopId, int gameId, String time, int hour, int to_member_id, String remark) {
        RequestParams params = new RequestParams();
        params.put("shopId", shopId);
        params.put("gameId", gameId);
        params.put("time", time);
        params.put("hour", hour);
        params.put("to_member_id", to_member_id);
        params.put("remark", remark);
        this.getRespone("God/toOrder/", params, new HttpResponePresenter.httpSubscriber<HttpResponeBean>() {
            @Override
            public void onStart() {
                orderView.startToOrder();
            }

            @Override
            public void mOnNext(HttpResponeBean response) {
                orderView.orderSucceed(response.getInfo());
            }

            @Override
            public void mOnCompleted() {
                orderView.orderFinish();
            }
        });
    }
}