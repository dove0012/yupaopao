package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.PhotosView;
import com.loopj.android.http.RequestParams;

/**
 * Created by liuzl on 2016/4/21.
 */
public class PhotosPresenter extends HttpResponePresenter {
    private PhotosView photosView;
    private PhotosPresenter instance = null;

    public PhotosPresenter(PhotosView photosView) {
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