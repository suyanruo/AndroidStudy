package com.example.zj.androidstudy.lazyLoad;

import android.view.View;

import com.example.zj.androidstudy.base.BaseFragment;

/**
 * Created on 4/7/21.
 * 在ViewPager中使用的Fragment继承此类可实现懒加载
 */

public abstract class LazyFragment extends BaseFragment {
  private boolean isLoaded;

  @Override
  protected void initViews(View view) {
  }

  @Override
  protected void initWorkers() {
  }

  @Override
  public void onResume() {
    super.onResume();
    if (!isLoaded) {
      lazyInit();
      isLoaded = true;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    isLoaded = false;
  }

  /**
   * 子类通过覆写此方法实现懒加载
   */
  public abstract void lazyInit();
}
