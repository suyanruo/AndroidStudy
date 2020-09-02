package com.example.zj.androidstudy.tool;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created on 2019-12-24.
 */
public class FileUtil {

  /**
        * 复制文件夹
        */
  public static boolean copyDirectory(File src, File dest) {
    if (!src.isDirectory()) {
      return false;
    }
    if (!dest.isDirectory() && !dest.mkdirs()) {
      return false;
    }
    File[] files = src.listFiles();
    for (File file : files) {
      File destFile = new File(dest, file.getName());
      if (file.isFile()) {
        if (!copyFile(file, destFile)) {
          return false;
        }
      } else if (file.isDirectory()) {
        if (!copyDirectory(file, destFile)) {
          return false;
        }
      }
    }
    return true;
  }


  /**
      * 复制文件
      */
  public static boolean copyFile(File src, File des) {
    if (!src.exists()) {
      Log.e("cppyFile", "file not exist:" + src.getAbsolutePath());
      return false;
    }
    if (!des.getParentFile().isDirectory() && !des.getParentFile().mkdirs()) {
      Log.e("cppyFile", "mkdir failed:" + des.getParent());
      return false;
    }
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(src));
      bos = new BufferedOutputStream(new FileOutputStream(des));
      byte[] buffer = new byte[4 * 1024];
      int count;
      while ((count = bis.read(buffer, 0, buffer.length)) != -1) {
        if (count > 0) {
          bos.write(buffer, 0, count);
        }
      }
      bos.flush();
      return true;
    } catch (Exception e) {
      Log.e("copyFile", "exception:", e);
    } finally {
      if (bis != null) {
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  // Checks if a volume containing external storage is available
  // for read and write.
  private boolean isExternalStorageWritable() {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
  }

  // Checks if a volume containing external storage is available to at least read.
  private boolean isExternalStorageReadable() {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY;
  }
}
