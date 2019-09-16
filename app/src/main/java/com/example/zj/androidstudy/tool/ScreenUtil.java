package com.example.zj.androidstudy.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.List;

public class ScreenUtil {

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
}