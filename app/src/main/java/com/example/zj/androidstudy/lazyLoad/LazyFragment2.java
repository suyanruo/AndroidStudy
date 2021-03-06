package com.example.zj.androidstudy.lazyLoad;

import android.view.View;

import com.example.zj.androidstudy.base.BaseFragment;

/**
 * Created on 4/7/21.
 * 使用add+show+hide管理的Fragment继承此类可实现懒加载
 */

public abstract class LazyFragment2 extends BaseFragment {
  private boolean isLoaded;

  @Override
  protected void initViews(View view) {
  }

  @Override
  protected void initWorkers() {
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    judgeLazyLoad();
  }

  @Override
  public void onResume() {
    super.onResume();
    judgeLazyLoad();
  }

  private void judgeLazyLoad() {
    //增加了Fragment是否可见的判断
    if (!isLoaded && !isHidden()) {
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
