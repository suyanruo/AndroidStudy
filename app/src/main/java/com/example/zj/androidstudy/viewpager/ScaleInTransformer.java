package com.example.zj.androidstudy.viewpager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Created on 4/12/21.
 * 设置ViewPager2划入划出动画
 */

public class ScaleInTransformer implements ViewPager2.PageTransformer {
  private static final float DEFAULT_MIN_SCALE = 0.85f;
  private static final float DEFAULT_CENTER = 0.5f;
  private float mMinScale = DEFAULT_MIN_SCALE;

  @Override
  public void transformPage(@NonNull View page, float position) {
    // require TargetSDK >= 21
    page.setElevation(-Math.abs(position));

    int width = page.getWidth();
    int height = page.getHeight();

    page.setPivotX(width / 2);
    page.setPivotY(height / 2);

    if (position < -1) {
      page.setScaleX(mMinScale);
      page.setScaleY(mMinScale);
      page.setPivotX(width);
    } else if (position <= 1) {
      float scaleFactor = (1 - Math.abs(position)) * (1 - mMinScale) + mMinScale;
      page.setScaleX(scaleFactor);
      page.setScaleY(scaleFactor);
      page.setPivotX(width * (DEFAULT_CENTER * (1 - position)));
    } else {
      page.setPivotX(0f);
      page.setScaleX(mMinScale);
      page.setScaleY(mMinScale);
    }
  }
}
