package com.example.zj.androidstudy.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

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
}
