package com.example.zj.androidstudy.adapter;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Created on 4/12/21.
 * ViewPager2 + Fragment
 */

public class ViewPager2FragmentAdapter extends FragmentStateAdapter {
  private SparseArray<Fragment> fragmentArray;

  public ViewPager2FragmentAdapter(@NonNull FragmentActivity fragmentActivity, SparseArray<Fragment> fragmentList) {
    super(fragmentActivity);
    this.fragmentArray = fragmentList;
  }

  public ViewPager2FragmentAdapter(@NonNull Fragment fragment, SparseArray<Fragment> fragmentList) {
    super(fragment);
    this.fragmentArray = fragmentList;
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    //FragmentStateAdapter内部自己会管理已实例化的fragment对象。所以不需要考虑复用的问题
    return fragmentArray.get(position);
  }

  @Override
  public int getItemCount() {
    return fragmentArray.size();
  }
}
