package com.example.zj.androidstudy.tool;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created on 2019-12-24.
 */
public class FileUtil {

  /**
   * 缓存非媒体文件
   * 您应该使用的方法取决于您需要缓存的文件类型。
   *
   * 小文件或包含敏感信息的文件：请使用 Context#getCacheDir()。
   * 大型文件或不含敏感信息的文件：请使用 Context#getExternalCacheDir()。
   * ref: https://developer.android.com/training/data-storage/use-cases?hl=zh-cn
   */
  public static String getFileCache(Context context) {
    return context.getExternalCacheDir().getAbsolutePath();
  }

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

  /**
   * 保存文件到共享缓存
   */
  public static void persistToFile(Context context, Object obj, String fileName) {
    if (obj == null) {
      return;
    }
    File dir = new File(getFileCache(context));
    if (!dir.exists()) {
      dir.mkdirs();
    }
    File file = new File(getFileCache(context) + File.separator + fileName);
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
      oos.writeObject(obj);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Object recoverFromFile(Context context, String fileName) {
    Object object = null;
    File file = new File(getFileCache(context) + File.separator + fileName);
    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        object = ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return object;
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
