package com.example.zj.androidstudy.camera;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zj.androidstudy.R;

public class CameraActivity extends AppCompatActivity {
  private int numbersOfCameras;
  private int faceBackCameraId;
  private int faceFrontCameraId;
  private int faceBackCameraOrientation;
  private int faceFrontCameraOrientation;

  private Camera camera;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);

  }

  /**
   * 获取相机信息
   */
  private void fetchCameraInfo() {
    //有多少个摄像头
    numbersOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numbersOfCameras; i++) {
      Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

      Camera.getCameraInfo(i, cameraInfo);

      //后置摄像头
      if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
        faceBackCameraId = i;
        faceBackCameraOrientation = cameraInfo.orientation;
      }
      //前置摄像头
      else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
        faceFrontCameraId = i;
        faceFrontCameraOrientation = cameraInfo.orientation;
      }
    }
  }

  private void openCamera() {
    camera = Camera.open();
//    camera = Camera.open(id);
  }

  private void get() {
    // 获取相机参数
    Camera.Parameters parameters = camera.getParameters();
    // 设置相机参数
    camera.setParameters(parameters);
    /*
      闪光灯配置参数
      Camera.Parameters.FLASH_MODE_AUTO 自动模式，当光线较暗时自动打开闪光灯；
      Camera.Parameters.FLASH_MODE_OFF 关闭闪光灯；
      Camera.Parameters.FLASH_MODE_ON 拍照时闪光灯；
      Camera.Parameters.FLASH_MODE_RED_EYE 闪光灯参数，防红眼模式。
     */
    parameters.getFlashMode();
    /*
      对焦模式配置参数
      Camera.Parameters.FOCUS_MODE_AUTO 自动对焦模式，摄影小白专用模式；
      Camera.Parameters.FOCUS_MODE_FIXED 固定焦距模式，拍摄老司机模式；
      Camera.Parameters.FOCUS_MODE_EDOF 景深模式，文艺女青年最喜欢的模式；
      Camera.Parameters.FOCUS_MODE_INFINITY 远景模式，拍风景大场面的模式；
      Camera.Parameters.FOCUS_MODE_MACRO 微焦模式，拍摄小花小草小蚂蚁专用模式；
     */
    parameters.getFocusMode();
    /*
      场景模式配置参数
      Camera.Parameters.SCENE_MODE_BARCODE 扫描条码场景，NextQRCode项目会判断并设置为这个场景；
      Camera.Parameters.SCENE_MODE_ACTION 动作场景，就是抓拍跑得飞快的运动员、汽车等场景用的；
      Camera.Parameters.SCENE_MODE_AUTO 自动选择场景；
      Camera.Parameters.SCENE_MODE_HDR 高动态对比度场景，通常用于拍摄晚霞等明暗分明的照片；
      Camera.Parameters.SCENE_MODE_NIGHT 夜间场景；
     */
    parameters.getSceneMode();
  }

  private void closeCamera() {
    camera.release();
  }
}
