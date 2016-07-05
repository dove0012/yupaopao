package com.example.dove0012.yupaopao.activitys;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.example.dove0012.yupaopao.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

public class IndexActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager) ViewPager vp;
    @Bind(R.id.ll) LinearLayout ll;

    private List<View> views;
    private ViewPagerAdapter vpAdapter;
    //引导图片资源
    private static final int[] pics = {
            R.drawable.whatsnew_00,
            R.drawable.whatsnew_01,
            R.drawable.whatsnew_02,
            R.drawable.whatsnew_03 };
    //底部小店图片
    private ImageView[] dots;
    //记录当前选中位置
    private int currentIndex;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_index);
    }

    @Override
    public void initWidget() {
        views = new ArrayList<View>();
        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY );
            //设置最后一张图片点击后跳转到MainActivity
            if ((i+1) == pics.length) {
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(IndexActivity.this,LoginActivity.class));
                        IndexActivity.this.finish();
                    }
                });
            }
            //iv.setImageResource(pics[i]);
            views.add(iv);
            Glide.with(this).load(pics[i]).into(iv);

        }
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.setOnPageChangeListener(this);
        //初始化底部小点
        initDots();
    }

    private void initDots() {
        dots = new ImageView[pics.length];
        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            //得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);//都设为灰色
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应

        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = positon;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ViewPagerAdapter extends PagerAdapter {
        //界面列表
        private List<View> views;
        public ViewPagerAdapter (List<View> views){
            this.views = views;
        }

        //销毁arg1位置的界面
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        //获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        //初始化arg1位置的界面
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }

        //判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }
}