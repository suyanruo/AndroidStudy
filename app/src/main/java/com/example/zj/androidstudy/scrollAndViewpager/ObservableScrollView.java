package com.example.zj.androidstudy.scrollAndViewpager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

/**
 * Created on 2019-12-19.
 */
public class ObservableScrollView extends NestedScrollView {
  private ScrollViewListener scrollViewListener = null;

  public ObservableScrollView(Context context) {
    super(context);
  }

  public ObservableScrollView(Context context, AttributeSet attrs,
                              int defStyle) {
    super(context, attrs, defStyle);
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setScrollViewListener(ScrollViewListener scrollViewListener) {
    this.scrollViewListener = scrollViewListener;
  }

  @Override
  protected void onScrollChanged(int x, int y, int oldx, int oldy) {
    super.onScrollChanged(x, y, oldx, oldy);
    if (scrollViewListener != null) {
      scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
    }
  }

  public interface ScrollViewListener {
    void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx, int oldy);
  }

}