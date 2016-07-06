package com.chong.coordinatorlayoutdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jaeger.library.StatusBarUtil;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout mRootlayout;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayout mHeadLl;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootlayout = (CoordinatorLayout) findViewById(R.id.root_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mHeadLl = (LinearLayout) findViewById(R.id.head_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.blog:
                        msg = "博客跳转";
                        break;
                    case R.id.weibo:
                        msg = "微博跳转";
                        break;
                    case R.id.action_settings:
                        msg = "设置";
                        break;
                }
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mHeadLl.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("多喝水");
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);
        mViewPager = (ViewPager) findViewById(R.id.main_vp_container);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //tablayout和viewpager建立联系为什么不用下面这个方法呢？自己去研究一下，可能收获更多
        // setupWithViewPager会先清空所有的Tab，然后再new新的Tab，会导致第一个Tab的背景图不显示
//        mTabLayout.setupWithViewPager(mViewPager);

        //设置毛玻璃效果和沉浸状态栏
        loadBlurAndSetStatusBar();

        ImageView headIv = (ImageView) findViewById(R.id.head_iv);
        Glide.with(this).load(R.mipmap.bg).bitmapTransform(new RoundedCornersTransformation(this,
                90, 0)).into(headIv);

    }

    /**
     * 设置毛玻璃效果和沉浸状态栏
     */
    private void loadBlurAndSetStatusBar() {
        StatusBarUtil.setTranslucent(MainActivity.this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        Glide.with(this).load(R.mipmap.bg).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mHeadLl.setBackground(resource);
                        mRootlayout.setBackground(resource);
                    }
                });

        Glide.with(this).load(R.mipmap.bg).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mCollapsingToolbarLayout.setContentScrim(resource);
                    }
                });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        String msg = "";
//        switch (item.getItemId()) {
//            case R.id.blog:
//                msg += "博客跳转";
//                break;
//            case R.id.weibo:
//                msg += "微博跳转";
//                break;
//            case R.id.action_settings:
//                msg += "设置";
//                break;
//        }
//        if (!TextUtils.equals(msg, "")) {
//            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
