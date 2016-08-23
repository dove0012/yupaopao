package com.example.liuzl.hitplane.role;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.liuzl.hitplane.GameImage;
import com.example.liuzl.hitplane.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liuzl on 2016/8/20.
 */
public class Enemy extends BaseRole implements GameImage{

    private List<Bitmap> bitmaps = new ArrayList<>();
    private GameImage gameImage;
    public boolean isBeAttack = false;
    private int x = 0,y = 0,i = 0;

    public Enemy(Bitmap bitmap) {
        super(bitmap);
        this.x = (new Random().nextInt(GameView.screenW - (imageW / 8)));
        this.y = -imageH / 2;

        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f);
        bitmaps.add(Bitmap.createBitmap(modelImage, 0, 0, imageW / 4, imageH, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 1, 0, imageW / 4, imageH, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 2, 0, imageW / 4, imageH, matrix, true));
        bitmaps.add(Bitmap.createBitmap(modelImage, (imageW / 4) * 3, 0, imageW / 4, imageH, matrix, true));
    }

    @Override
    public Bitmap getBitmap() {
        this.y += 5;
        if (isBeAttack) {
            ((Explosion)gameImage).setX(x);
            ((Explosion)gameImage).setY(y);
            newImage = gameImage.getBitmap();
            i++;
            if (i == 7){
                this.y = GameView.screenH;
            }
        } else {
            newImage = bitmaps.get(index);
            index ++;
            if (index == bitmaps.size()) index = 0;
        }
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

    public void beAttack(GameImage gameImage) {
        this.isBeAttack = true;
        this.gameImage = gameImage;
    }
}