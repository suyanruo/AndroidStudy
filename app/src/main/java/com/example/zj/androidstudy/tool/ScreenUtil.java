package com.example.zj.androidstudy.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.DisplayCutout;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ScreenUtil {
  private static int statusBarHeight = 0;

  public static DisplayMetrics getScreenSize(Context context) {
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    manager.getDefaultDisplay().getMetrics(metrics);
    return metrics;
  }

  public static int getScreenWidth(Context context) {
    if (context == null) return 0;
    return getScreenSize(context).widthPixels;
  }

  public static int getScreenHeight(Context context) {
    if (context == null) return 0;
    return getScreenSize(context).heightPixels;
  }

  public static float getDeviceDensity(Context context) {
    if (context == null) return 0;
    return getScreenSize(context).density;
  }

  public static int dp2px(Context context, int dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
  }

  public static int px2dip(float pxValue) {
    float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 判断是否为刘海屏
   *
   * @param activity
   * @return
   */
  public static boolean isNotchSreen(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

      WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();

      if (windowInsets != null) {

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();

        if (displayCutout != null) {

          List<Rect> rects = displayCutout.getBoundingRects();

          //通过判断是否存在rects来确定是否刘海屏手机

          if (rects != null && rects.size() > 0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  /**
   * 是否有刘海屏
   *
   * @return
   */
  public static boolean hasNotchInScreen(Activity activity) {

    // android  P 以上有标准 API 来判断是否有刘海屏
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
      if (windowInsets != null) {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
          List<Rect> rects = displayCutout.getBoundingRects();
          //通过判断是否存在rects来确定是否刘海屏手机
          if (rects != null && rects.size() > 0) {
            return true;
          }
        }
      }
    }
    // 通过其他方式判断是否有刘海屏  目前官方提供有开发文档的就 小米，vivo，华为（荣耀），oppo
    String manufacturer = Build.MANUFACTURER;
    if (TextUtils.isEmpty(manufacturer)) {
      return false;
    } else if (manufacturer.equalsIgnoreCase("HUAWEI")) {
      return hasNotchHw(activity);
    } else if (manufacturer.equalsIgnoreCase("xiaomi")) {
      return hasNotchXiaoMi(activity);
    } else if (manufacturer.equalsIgnoreCase("oppo")) {
      return hasNotchOPPO(activity);
    } else if (manufacturer.equalsIgnoreCase("vivo")) {
      return hasNotchVIVO(activity);
    } else {
      return false;
    }
  }

  /**
   * 判断vivo是否有刘海屏
   * https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
   *
   * @param activity
   * @return
   */
  private static boolean hasNotchVIVO(Activity activity) {
    try {
      Class<?> c = Class.forName("android.util.FtFeature");
      Method get = c.getMethod("isFeatureSupport", int.class);
      return (boolean) (get.invoke(c, 0x20));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 判断oppo是否有刘海屏
   * https://open.oppomobile.com/wiki/doc#id=10159
   *
   * @param activity
   * @return
   */
  private static boolean hasNotchOPPO(Activity activity) {
    return activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
  }

  /**
   * 判断xiaomi是否有刘海屏
   * https://dev.mi.com/console/doc/detail?pId=1293
   *
   * @param activity
   * @return
   */
  private static boolean hasNotchXiaoMi(Activity activity) {
    try {
      Class<?> c = Class.forName("android.os.SystemProperties");
      Method get = c.getMethod("getInt", String.class, int.class);
      return (int) (get.invoke(c, "ro.miui.notch", 0)) == 1;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 判断华为是否有刘海屏
   * https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
   *
   * @param activity
   * @return
   */
  private static boolean hasNotchHw(Activity activity) {

    try {
      ClassLoader cl = activity.getClassLoader();
      Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
      Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
      return (boolean) get.invoke(HwNotchSizeUtil);
    } catch (Exception e) {
      return false;
    }
  }

  public static int getStatusBarHeight(Context context) {
    if (statusBarHeight != 0) {
      return statusBarHeight;
    } else {
      Class<?> c = null;
      Object obj = null;
      Field field = null;
      int sbar = 0;

      try {
        c = Class.forName("com.android.internal.R$dimen");
        obj = c.newInstance();
        field = c.getField("status_bar_height");
        int x = Integer.parseInt(field.get(obj).toString());
        sbar = context.getResources().getDimensionPixelSize(x);
      } catch (Exception var7) {
        var7.printStackTrace();
      }

      statusBarHeight = sbar;
      return statusBarHeight;
    }
  }

  public static int getMeasureHeight(View view) {
    view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    return view.getMeasuredHeight();
  }

  /**
   * 禁用系统软键盘
   */
  public static void disableSysKeyboard(Activity context, EditText etInput) {
    context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    try {
      Class<EditText>  cls = EditText.class;
      Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
      setShowSoftInputOnFocus.setAccessible(true);
      setShowSoftInputOnFocus.invoke(etInput, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}