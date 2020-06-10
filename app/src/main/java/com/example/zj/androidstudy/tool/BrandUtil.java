package com.example.zj.androidstudy.tool;

/**
 * Created on 2020/6/5.
 * 判断手机品牌 https://github.com/Blankj/AndroidUtilCode/issues/642
 */
public class BrandUtil {
  public static String getBrand() {
    return android.os.Build.BRAND;
  }

  public static boolean isMi() {
    return getBrand().toLowerCase().equals("xiaomi");
  }

  public static boolean isOnePlus() {
    return getBrand().toLowerCase().equals("oneplus");
  }

  public static boolean isHTC() {
    return getBrand().toLowerCase().equals("htc");
  }

  public static boolean isHuawei() {
    return getBrand().toLowerCase().equals("honor") || getBrand().toLowerCase().equals("huawei");
  }

  public static boolean isSmartisan() {
    return getBrand().toLowerCase().equals("smartisan");
  }

  public static boolean isMeizu() {
    return getBrand().toLowerCase().equals("meizu");
  }

  public static boolean isSamsung() {
    return getBrand().toLowerCase().equals("samsung");
  }

  public static boolean isVIVO() {
    return getBrand().toLowerCase().equals("vivo");
  }

  public static boolean isOPPO() {
    return getBrand().toLowerCase().equals("oppo");
  }

  public static boolean isSony() {
    return getBrand().toLowerCase().equals("sony");
  }

  public static boolean isGionee() {
    return getBrand().toLowerCase().equals("gionee");
  }

  public static boolean isLenovo() {
    return getBrand().toLowerCase().equals("lenovo") || getBrand().toLowerCase().equals("zuk");
  }

  public static boolean isCoolpad() {
    return getBrand().toLowerCase().equals("coolpad");
  }

  public static boolean isNubia() {
    return getBrand().toLowerCase().equals("nubia");
  }

  public static boolean isNokia() {
    return getBrand().toLowerCase().equals("nokia");
  }
}
