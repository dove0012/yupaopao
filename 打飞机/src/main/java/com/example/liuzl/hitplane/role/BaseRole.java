package com.example.liuzl.hitplane.role;

import android.graphics.Bitmap;

import com.example.liuzl.hitplane.GameView;

/**
 * Created by liuzl on 2016/8/17.
 */
public abstract class BaseRole {
    public Bitmap modelImage;  //原型图片
    public Bitmap newImage;  //新的图片
    public int imageW;
    public int imageH;
    public int index = 0;

    public BaseRole(Bitmap bitmap) {
        this.modelImage = bitmap;
        imageW = bitmap.getWidth();
        imageH = bitmap.getHeight();
        newImage = Bitmap.createBitmap(GameView.screenW, GameView.screenH, Bitmap.Config.ARGB_8888);
    }
}