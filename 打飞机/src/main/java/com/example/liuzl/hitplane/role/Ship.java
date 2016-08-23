package com.example.liuzl.hitplane.role;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.example.liuzl.hitplane.GameImage;
import com.example.liuzl.hitplane.GameView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzl on 2016/8/17.
 */
public class Ship extends BaseRole implements GameImage{

    private List<Bitmap> bitmaps = new ArrayList<>();
    private int x = 0,y = 0;

    public Ship(Bitmap bitmap) {
        super(bitmap);
        this.x = (GameView.screenW - imageW / 8) / 2;
        this.y = GameView.screenH - imageH / 2 - 10;

        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f);
        bitmaps.add(Bitmap.createBitmap(modelImage, 0, 0, imageW / 4, imageH, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 1, 0, imageW / 4, imageH, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 2, 0, imageW / 4, imageH, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 3, 0, imageW / 4, imageH, matrix, true));
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

    public void setX(int eventX) {
        this.x = eventX - imageW / 16;
    }

    public void setY(int eventY) {
        this.y = eventY - (imageH / 4);
    }
}