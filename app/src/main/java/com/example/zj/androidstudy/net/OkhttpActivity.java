package com.example.zj.androidstudy.net;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;
import com.example.zj.androidstudy.tool.HttpUtil;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created on 2020-05-19.
 */
public class OkhttpActivity extends BaseActivity {
  private static final String TAG = "OkhttpActivity";
  private String url = "https://india-oss-test.oss-ap-south-1.aliyuncs.com/ml.jpg?Expires=1589866177&OSSAccessKeyId=LTAIJGoKatmSawAC&Signature=DcrtGGQhyWISliM8UwXOaB2NYHs%3D";

  private ImageView imageView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_okhttp);

    imageView = findViewById(R.id.iv_download);
    requestPermission();
  }


  private void downloadImage() {
    HttpUtil.get().download(url, "download", new HttpUtil.OnDownloadListener() {
      @Override
      public void onDownloadSuccess(String path) {
        Log.e(TAG, "下载完成");
        //图片路径filepath

        Bitmap bitmap = BitmapFactory.decodeFile(path);//从路径加载出图片bitmap
        imageView.setImageBitmap(bitmap);//ImageView显示图片
      }

      @Override
      public void onDownloading(int progress) {
        Log.e(TAG, "下载进度：" + progress);
      }

      @Override
      public void onDownloadFailed() {
        Log.e(TAG, "下载失败");
      }
    });
  }

  /**
   * 请求授权
   */
  private void requestPermission() {

    if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
      //进行授权
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
    } else {
      // 下载图片
      downloadImage();
    }
  }

  /**
   * 权限申请返回结果
   *
   * @param requestCode  请求码
   * @param permissions  权限数组
   * @param grantResults 申请结果数组，里面都是int类型的数
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意权限申请
          downloadImage();
        } else { //拒绝权限申请
          Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_SHORT).show();
        }
        break;
      default:
        break;
    }
  }
}
