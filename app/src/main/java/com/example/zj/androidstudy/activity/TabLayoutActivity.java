package com.example.zj.androidstudy.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.adapter.FragmentAdapter;
import com.example.zj.androidstudy.base.BaseActivity;
import com.example.zj.androidstudy.fragment.ListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabLayoutActivity extends BaseActivity {
    private String[] mTitles = {"关注", "推荐", "精选", "热点", "体育", "视频", "图片", "娱乐", "科技", "财经", "军事", "健康"};
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        Toolbar toolbar = findViewById(R.id.toolbar_tab);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mViewPager = findViewById(R.id.viewpager_tab);
        mTabLayout = findViewById(R.id.tabl_tab);
        mDrawerLayout = findViewById(R.id.dl_main_drawer);
        mNavigationView = findViewById(R.id.nv_main_navigation);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // 改变item选中状态
//                    item.setChecked(true);
                    Toast.makeText(getApplicationContext(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    mDrawerLayout.closeDrawers();
                    return false;
                }
            });
        }

        initViewPager();
    }

    private void initViewPager() {
        mTabLayout = findViewById(R.id.tabl_tab);

        List<String> titles = new ArrayList<>(Arrays.asList(mTitles));
        List<Fragment> fragments = new ArrayList<>();
        for (String title: titles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
            fragments.add(new ListFragment());
        }
        // 定义Adapter
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        // 设置viewpager的适配器
        mViewPager.setAdapter(fragmentAdapter);
        // 将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        // 给TagLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
