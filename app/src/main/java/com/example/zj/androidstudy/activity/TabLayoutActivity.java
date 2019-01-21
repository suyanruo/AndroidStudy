package com.example.zj.androidstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.adapter.FragmentAdapter;
import com.example.zj.androidstudy.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabLayoutActivity extends BaseActivity {
    private String[] mTitles = {"关注", "推荐", "精选", "热点", "体育", "视频", "图片", "娱乐", "科技", "财经", "军事", "健康"};
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        Toolbar toolbar = findViewById(R.id.toolbar_tab);
        mViewPager = findViewById(R.id.viewpager_tab);
        mTabLayout = findViewById(R.id.tabl_tab);

        setSupportActionBar(toolbar);
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
}
