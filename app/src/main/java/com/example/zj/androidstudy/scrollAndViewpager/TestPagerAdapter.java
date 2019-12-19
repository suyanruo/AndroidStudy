package com.example.zj.androidstudy.scrollAndViewpager;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created on 2019-12-19.
 */
public class TestPagerAdapter extends FragmentPagerAdapter {
  List<Fragment> fragments;

  public TestPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    this.fragments = fragments;
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

}
