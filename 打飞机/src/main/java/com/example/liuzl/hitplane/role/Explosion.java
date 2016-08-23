package com.example.liuzl.hitplane.role;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.example.liuzl.hitplane.GameImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzl on 2016/8/20.
 */
public class Explosion extends BaseRole implements GameImage{

    private List<Bitmap> bitmaps = new ArrayList<>();
    private int x = 0,y = 0;

    public Explosion(Bitmap bitmap) {
        super(bitmap);

        Matrix matrix = new Matrix();
        matrix.postScale(0.7f,0.7f);
        bitmaps.add(Bitmap.createBitmap(modelImage, 0, 0, imageW / 4, imageH / 2, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 1, 0, imageW / 4, imageH / 2, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 2, 0, imageW / 4, imageH / 2, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 3, 0, imageW / 4, imageH / 2, matrix, true));

        bitmaps.add(Bitmap.createBitmap(modelImage, 0, imageH / 2, imageW / 4, imageH / 2, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 1, imageH / 2, imageW / 4, imageH / 2, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 2, imageH / 2, imageW / 4, imageH / 2, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 3, imageH / 2, imageW / 4, imageH / 2, matrix, true));
    }

    @Override
    public Bitmap getBitmap() {
        newImage = bitmaps.get(index);
        index ++;
        if (index == bitmaps.size()) index = 0;
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