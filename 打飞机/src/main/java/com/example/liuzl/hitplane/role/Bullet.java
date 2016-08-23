package com.example.liuzl.hitplane.role;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.liuzl.hitplane.GameImage;

/**
 * Created by liuzl on 2016/8/20.
 */
public class Bullet extends BaseRole implements GameImage{

    private int x = 0,y = 0;

    public Bullet(Bitmap bitmap) {
        super(bitmap);
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f);
        newImage = Bitmap.createBitmap(modelImage, 0, 0, imageW, imageH, matrix, true);
    }

    @Override
    public Bitmap getBitmap() {
        y -= 40;
        return newImage;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}