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
    private static ShopAddressPresenter instance = null;

    private static synchronized void syncInit(ShopAdressView shopAdressView) {
        if (instance == null) {
            instance = new ShopAddressPresenter(shopAdressView);
        }
    }

    public static ShopAddressPresenter getInstance(ShopAdressView shopAdressView) {
        if (instance == null) {
            syncInit(shopAdressView);
        }
        instance.shopAdressView = shopAdressView;
        return instance;
    }

    private ShopAddressPresenter(ShopAdressView shopAdressView) {
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