package com.example.dove0012.yupaopao.activitys;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.KeyEvent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.dove0012.yupaopao.R;
import com.example.dove0012.yupaopao.adapters.MyFragmentPagerAdapter;
import com.example.dove0012.yupaopao.fragments.WdFragment;
import com.example.dove0012.yupaopao.fragments.YsFragment;
import com.example.dove0012.yupaopao.fragments.XxFragment;
import com.example.dove0012.yupaopao.fragments.TsFragment;
import com.example.dove0012.yupaopao.utils.ScreenUtils;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

    @Bind(R.id.main_pager) ViewPager mainPager;
    @Bind(R.id.collect_cursor) ImageView mImgLineImageView;

    private boolean is2CallBack = false;
    private ArrayList<Fragment> fragmentList;
    private int currIndex = 0;// 当前页卡编号
    private int screenW = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initWidget() {
        screenW = ScreenUtils.getScreenW(this);
        InitViewPager();
        InitImage();
    }

    private void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        Fragment ys = new YsFragment();
        Fragment ts = new TsFragment();
        Fragment xx = new XxFragment();
        Fragment wd = new WdFragment();

        fragmentList.add(ys);
        fragmentList.add(ts);
        fragmentList.add(xx);
        fragmentList.add(wd);

        mainPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mainPager.setCurrentItem(currIndex);
        mainPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                int offset = screenW / fragmentList.size();
                Animation animation = new TranslateAnimation(offset * currIndex, offset * arg0, 0, 0);
                currIndex = arg0;
                animation.setFillAfter(true);// 图片停在动画结束位置
                animation.setDuration(300);
                mImgLineImageView.startAnimation(animation);
            }
        });
    }

    private void InitImage() {
        mImgLineImageView = (ImageView) findViewById(R.id.collect_cursor);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_collect_line);
        int width = screenW / fragmentList.size();
        //防止图片尺寸在更大的分辨率的手机上放大不了而出现闪退做相应处理
        if (width == 0 || width > bitmap.getWidth()) {
            width = bitmap.getWidth();
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, bitmap.getHeight());
        mImgLineImageView.setImageBitmap(bitmap);
    }

    @OnClick({ R.id.collect_title1, R.id.collect_title2, R.id.collect_title3, R.id.collect_title4 })
    public void click(View v) {
        switch (v.getId()){
            case R.id.collect_title1:
                mainPager.setCurrentItem(0, false);
                break;
            case R.id.collect_title2:
                mainPager.setCurrentItem(1, false);
                break;
            case R.id.collect_title3:
                mainPager.setCurrentItem(2, false);
                break;
            case R.id.collect_title4:
                mainPager.setCurrentItem(3, false);
                break;
        }
    }

    /*
    * 双击退出事件
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK){
            if(!is2CallBack){
                is2CallBack = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        is2CallBack = false;
                    }
                }, 2000);
            } else {
                this.finish();
                System.exit(0);
            }
        }
        return true;
    }
}