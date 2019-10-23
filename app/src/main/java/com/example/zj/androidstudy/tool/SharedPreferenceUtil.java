package com.example.zj.androidstudy.tool;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

  public static String getString(Context context, String name, String defaultValue) {
    SharedPreferences sharedPreferences;
    sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

    return sharedPreferences.getString(name, defaultValue);
  }

  public static int getInt(Context context, String name, int defaultValue) {
    SharedPreferences sharedPreferences;
    sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

    return sharedPreferences.getInt(name, defaultValue);
  }

  public static boolean getBoolean(Context context, String name, boolean defaultValue) {
    SharedPreferences sharedPreferences;
    sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

    return sharedPreferences.getBoolean(name, defaultValue);
  }

  public static void saveString(Context context, String name, String value) {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString(name, value);
    editor.apply();
  }

  public static void saveInt(Context context, String name, int value) {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putInt(name, value);
    editor.apply();
  }

  public static void saveBoolean(Context context, String name, boolean value) {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putBoolean(name, value);
    editor.apply();
  }
}
