package com.example.liuzl.hitplane.role;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.liuzl.hitplane.GameImage;
import com.example.liuzl.hitplane.GameView;

/**
 * Created by liuzl on 2016/8/13.
 */
public class Background extends BaseRole implements GameImage{

    public Background(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public Bitmap getBitmap(){
        Paint p = new Paint();
        Canvas canvas = new Canvas(newImage);
        canvas.drawBitmap(modelImage,
                new Rect(0, 0, imageW, imageH),
                new Rect(0, index, GameView.screenW, GameView.screenH + index),
                p);

        canvas.drawBitmap(modelImage,
                new Rect(0, 0, imageW, imageH),
                new Rect(0, -GameView.screenH + index, GameView.screenW, index),
                p);
        index += 10;
        if (index >= GameView.screenH) {
            index = 0;
        }
        return newImage;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}