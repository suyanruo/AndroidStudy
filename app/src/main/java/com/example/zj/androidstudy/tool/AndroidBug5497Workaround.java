package com.example.zj.androidstudy.tool;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * issue 5497
 * 主要解决web页中靠底部 input 点击获取焦点后没能自动滚到键盘上面，影响输入体验
 * <p>
 * 使用：在 webView 对应类中设置AndroidBug5497Workaround.assistActivity(this);
 * <p>
 * https://stackoverflow.com/questions/7417123/android-how-to-adjust-layout-in-full-screen-mode-when-softkeyboard-is-visible/19494006#19494006
 *
 * @author qingguo
 */

public class AndroidBug5497Workaround {
  private Activity activity;
  private View mChildOfContent;
  private int usableHeightPrevious;
  private FrameLayout.LayoutParams frameLayoutParams;

  public static void assistActivity(Activity activity) {
    new AndroidBug5497Workaround(activity);
  }

  private AndroidBug5497Workaround(Activity activity) {
    this.activity = activity;
    FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
    mChildOfContent = content.getChildAt(0);
    mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        possiblyResizeChildOfContent();
      }
    });
    frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
  }

  private void possiblyResizeChildOfContent() {
    int usableHeightNow = computeUsableHeight();
    if (usableHeightNow != usableHeightPrevious) {
      int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();

      //这个判断是为了解决19之前的版本不支持沉浸式状态栏导致布局显示不完全的问题
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        usableHeightSansKeyboard -= getStatusBarHeight();
      }

      int heightDifference = usableHeightSansKeyboard - usableHeightNow;
      if (heightDifference > (usableHeightSansKeyboard / 4)) {
        // keyboard probably just became visible
        frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
      } else {
        // keyboard probably just became hidden
        frameLayoutParams.height = usableHeightNow;
      }
      mChildOfContent.requestLayout();
      usableHeightPrevious = usableHeightNow;
    }
  }

  private int computeUsableHeight() {


    Rect r = new Rect();
    mChildOfContent.getWindowVisibleDisplayFrame(r);
    //这个判断是为了解决19之后的版本在弹出软键盘时，键盘和推上去的布局（adjustResize）之间有黑色区域的问题
    //todo 之所以会出现黑色区域，是由于基础activity添加了FLAG_TRANSLUCENT_STATUS导致的，所以当clear该flag后，不需要加getStatusBarHeight
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      return (r.bottom - r.top) + getStatusBarHeight();
    }
    return (r.bottom - r.top);
  }

  private int getStatusBarHeight() {
    Rect frame = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    return frame.top;
  }
}
