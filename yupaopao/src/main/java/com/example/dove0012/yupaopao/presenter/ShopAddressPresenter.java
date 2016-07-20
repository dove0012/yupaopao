package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.ShopAdressView;
import com.example.dove0012.yupaopao.utils.JsonUtils;
import org.json.JSONArray;

/**
 * Created by dove0012 on 16/4/6.
 */
public class ShopAddressPresenter extends HttpResponePresenter {
    private ShopAdressView shopAdressView;

    public ShopAddressPresenter(ShopAdressView shopAdressView) {
        this.shopAdressView = shopAdressView;
    }

    public void getShopAdress() {
        this.getRespone("God/shopAdress", new httpSubscriber<HttpResponeBean>() {
            @Override
            public void onStart() {
                shopAdressView.startShopAdress();
            }

            public void mOnCompleted() {
                shopAdressView.shopAdressFinish();
            }

            public void mOnNext(HttpResponeBean respone) {
                shopAdressView.setShopAdress(respone.getInfo());
            }
        });
    }
}