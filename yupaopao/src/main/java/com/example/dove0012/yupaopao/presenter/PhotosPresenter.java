package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.PhotosView;
import com.loopj.android.http.RequestParams;

/**
 * Created by liuzl on 2016/4/21.
 */
public class PhotosPresenter extends HttpResponePresenter {
    private PhotosView photosView;
    private static PhotosPresenter instance = null;

    private static synchronized void syncInit(PhotosView photosView) {
        if (instance == null) {
            instance = new PhotosPresenter(photosView);
        }
    }

    public static PhotosPresenter getInstance(PhotosView photosView) {
        if (instance == null) {
            syncInit(photosView);
        }
        instance.photosView = photosView;
        return instance;
    }

    private PhotosPresenter(PhotosView photosView) {
        this.photosView = photosView;
    }

    public void takePhotos(int member_id) {
        RequestParams params = new RequestParams();
        params.put("member_id", member_id);
        this.getRespone("God/photos/", params, new HttpResponePresenter.httpSubscriber<HttpResponeBean>() {
            @Override
            public void mOnNext(HttpResponeBean response) {
                photosView.photosSucceed(response.getInfo());
            }
        });
    }
}