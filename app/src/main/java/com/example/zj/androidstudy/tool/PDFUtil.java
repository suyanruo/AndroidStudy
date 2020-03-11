package com.example.zj.androidstudy.tool;

import android.text.TextUtils;

import com.example.zj.androidstudy.pdf.PDFFileInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created on 2019-12-24.
 */
public class PDFUtil {

  /**
   * 读取文件的最后修改时间的方法
   */
  public static String getFileLastModifiedTime(File f) {
    Calendar cal = Calendar.getInstance();
    long time = f.lastModified();
    SimpleDateFormat formatter = new
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    cal.setTimeInMillis(time);
    return formatter.format(cal.getTime());
  }
  public static PDFFileInfo getFileInfoFromFile(File file) {
    PDFFileInfo fileInfo = new PDFFileInfo();
    fileInfo.setFileName(file.getName());
    fileInfo.setFilePath(file.getPath());
    fileInfo.setFileSize(file.length());
//        fileInfo.setDirectory(file.isDirectory());
    fileInfo.setTime(PDFUtil.getFileLastModifiedTime(file));
    int lastDotIndex = file.getName().lastIndexOf(".");
    if (lastDotIndex > 0) {
      String fileSuffix = file.getName().substring(lastDotIndex + 1);
//            fileInfo.setSuffix(fileSuffix);
    }
    return fileInfo;
  }
  public static String FormetFileSize(long fileS) {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    String wrongSize = "0B";
    if (fileS == 0) {
      return wrongSize;
    }
    if (fileS < 1024) {
      fileSizeString = df.format((double) fileS) + "B";
    } else if (fileS < 1048576) {
      fileSizeString = df.format((double) fileS / 1024) + "KB";
    } else if (fileS < 1073741824) {
      fileSizeString = df.format((double) fileS / 1048576) + "MB";
    } else {
      fileSizeString = df.format((double) fileS / 1073741824) + "GB";
    }
    return fileSizeString;
  }
  /**
   * 利用文件url转换出文件名
   *
   * @param url
   * @return
   */
  public static String parseName(String url) {
    String fileName = null;
    try {
      fileName = url.substring(url.lastIndexOf("/") + 1);
    } finally {
      if (TextUtils.isEmpty(fileName)) {
        fileName = String.valueOf(System.currentTimeMillis());
      }
    }
    return fileName;
  }
  /**
   * 将url进行encode，解决部分手机无法下载含有中文url的文件的问题（如OPPO R9）
   *
   * @param url
   * @return
   * @author xch
   */
  public static String toUtf8String(String url) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < url.length(); i++) {
      char c = url.charAt(i);
      if (c >= 0 && c <= 255) {
        sb.append(c);
      } else {
        byte[] b;
        try {
          b = String.valueOf(c).getBytes("utf-8");
        } catch (Exception ex) {
          System.out.println(ex);
          b = new byte[0];
        }
        for (int j = 0; j < b.length; j++) {
          int k = b[j];
          if (k < 0) {
            k += 256;
          }
          sb.append("%" + Integer.toHexString(k).toUpperCase());
        }
      }
    }
    return sb.toString();
  }
  public static String parseFormat(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }
}
