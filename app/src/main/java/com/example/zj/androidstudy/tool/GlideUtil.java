package com.example.zj.androidstudy.tool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created on 2020/11/29.
 */
public class GlideUtil {

  public static void loadImage(Context context, String url, ImageView imageView) {
    Glide.with(context)
        .load(url)
        .placeholder(new ColorDrawable(Color.GRAY))
        .error(new ColorDrawable(Color.RED))
        .fallback(new ColorDrawable(Color.CYAN))
        .into(imageView);
  }

  public static void loadImage(Context context, int resId, ImageView imageView) {
    Glide.with(context)
        .load(resId)
        .into(imageView);
  }
}
