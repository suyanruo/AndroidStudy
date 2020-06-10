package com.example.zj.androidstudy.tool;

import android.hardware.Camera;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * Created on 2020/6/5.
 */
public class PermissionUtil {

  /**
   * 检测相机权限，这个因为直接拿camera还不一定行，当前测试机型：oppo 魅族 小米
   * 相机	CAMERA	危险	使用摄像头做相关工作
   *
   */
  public static boolean checkCameraPermissions() {
    Camera mCamera = null;
    try {
      // 大众路线 过了基本就是有权限
      mCamera = Camera.open();
      Camera.Parameters mParameters = mCamera.getParameters();
      mCamera.setParameters(mParameters);

      // 特殊路线，oppo和vivo得根据rom去反射mHasPermission
      if (BrandUtil.isOPPO() || BrandUtil.isVIVO()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          Field fieldPassword;
          if (mCamera == null) {
            mCamera = Camera.open();
          }
          //通过反射去拿相机是否获得了权限
          fieldPassword = mCamera.getClass().getDeclaredField("mHasPermission");
          fieldPassword.setAccessible(true);
          Boolean result = (Boolean) fieldPassword.get(mCamera);
          if (mCamera != null) {
            mCamera.release();
          }
          mCamera = null;
          return result;
        }
      }
    } catch (Exception e) {

      return false;
    } finally {
      if (mCamera != null) {
        mCamera.release();
      }
      mCamera = null;
    }
    return true;
  }
}
