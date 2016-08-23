package com.example.liuzl.hitplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.liuzl.hitplane.role.Background;
import com.example.liuzl.hitplane.role.Bullet;
import com.example.liuzl.hitplane.role.Enemy;
import com.example.liuzl.hitplane.role.Explosion;
import com.example.liuzl.hitplane.role.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzl on 2016/8/16.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{
    private SurfaceHolder holder;
    private GameThread gameThread;
    private boolean isOnTouch = false;

    public ArrayList<GameImage> gameImageList = new ArrayList<>();  //游戏角色集合
    public ArrayList<GameImage> bulletList = new ArrayList<>();  //游戏角色集合

    //初始化游戏角色
    public Bitmap bgImage;  //背景图片
    public Bitmap shipImage;  //自己的战机图片
    public Bitmap enemyImage;  //敌人的战机图片
    public Bitmap bulletImage;  //子弹图片
    public Bitmap explosionImage;  //子弹图片

    public static int screenW,screenH;  //屏幕宽度和高度
    public Bitmap imageCache;  //图片缓存

    public GameView(Context context) {
        super(context);

        holder = this.getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
        gameThread = new GameThread(holder);//创建一个绘图线程
    }

    public void init() {
        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        shipImage = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        enemyImage = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        bulletImage = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        explosionImage = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);

        imageCache = Bitmap.createBitmap(GameView.screenW, GameView.screenH, Bitmap.Config.ARGB_8888);

        gameImageList.add(new Background(bgImage));
        gameImageList.add(new Ship(shipImage));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        GameView.screenW = this.getWidth();
        GameView.screenH = this.getHeight();
        gameThread.isRun = true;
        init();
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.isRun = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            isOnTouch = true;
            for (GameImage gameImage : gameImageList) {
                if (gameImage instanceof Ship) {
                    ((Ship) gameImage).setX((int) event.getX());
                    ((Ship) gameImage).setY((int) event.getY());
                    break;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isOnTouch = false;
        }
        return true;
    }

    class GameThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;

        public GameThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            Paint p = new Paint(); //创建画笔
            Canvas canvas = null;
            Canvas newCanvas = null;
            int shipX = 0,shipY = 0;
            int enemy_index = 0,bullet_index = 0;
            while (isRun) {
                try {
                    newCanvas = new Canvas(imageCache);
                    for(GameImage gameImage : (ArrayList<GameImage>)gameImageList.clone()) {
                        if (gameImage instanceof Enemy) {
                            for(GameImage bullet : (ArrayList<GameImage>)bulletList.clone()) {
                                //敌机中弹判断
                                if (!((Enemy) gameImage).isBeAttack && bullet.getX() > gameImage.getX() && (bullet.getX() < (gameImage.getX()+(((Enemy) gameImage).imageW/8))) && bullet.getY() > gameImage.getY() && (bullet.getY() < (gameImage.getY()+(((Enemy) gameImage).imageH/2)))) {
                                    bulletList.remove(bullet);
                                    ((Enemy)gameImage).beAttack(new Explosion(explosionImage));
                                }
                            }
                            if (gameImage.getY() > screenH)
                                gameImageList.remove(gameImage);
                        } else if (gameImage instanceof Ship) {
                            shipX = gameImage.getX();
                            shipY = gameImage.getY();
                        }
                        newCanvas.drawBitmap(gameImage.getBitmap(), gameImage.getX(), gameImage.getY(), p);
                    }

                    for(GameImage bullet : (ArrayList<GameImage>)bulletList.clone()) {
                        if (bullet.getY() < 0) {
                            bulletList.remove(bullet);
                        }
                        newCanvas.drawBitmap(bullet.getBitmap(), bullet.getX(), bullet.getY(), p);
                    }

                    //添加敌机
                    if (enemy_index == 30) {
                        gameImageList.add(new Enemy(enemyImage));
                        enemy_index = 0;
                    }
                    enemy_index ++;

                    //添加战机发射子弹
                    if (isOnTouch && bullet_index == 2) {
                        Bullet bullet = new Bullet(bulletImage);
                        bullet.setX(shipX + (((shipImage.getWidth() / 8) - (bulletImage.getWidth() / 2)) / 2));
                        bullet.setY(shipY);
                        bulletList.add(bullet);
                        bullet_index = 0;
                    } else if (isOnTouch) {
                        bullet_index ++;
                    }

                    canvas = holder.lockCanvas();
                    canvas.drawBitmap(imageCache, 0, 0, p);
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}