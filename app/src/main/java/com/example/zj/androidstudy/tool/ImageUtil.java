package com.example.zj.androidstudy.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

    /**
     * 根据传入的bitmap，将之分割成type类型的若干块bitmap
     * @param context
     * @param type 假设type为n，分割成n * n的模块
     * @param picBitmap
     * @return
     */
    public static List<Bitmap> createBitmapItem(Context context, int type, Bitmap picBitmap) {
        Bitmap temBitmap;
        List<Bitmap> bitmapItems = new ArrayList<>();
        // 定义每块bitmap item的宽高
        int itemWidth = picBitmap.getWidth() / type;
        int itemHeight = picBitmap.getHeight() / type;
        for (int i = 1; i <= type; i++) {
            for (int j = 1; j <= type; j++) {
                temBitmap = Bitmap.createBitmap(picBitmap, (j - 1) * itemWidth, (i - 1) * itemHeight, itemWidth, itemHeight);
                bitmapItems.add(temBitmap);
            }
        }
        return bitmapItems;
    }

    /**
     * 处理图片到合适大小
     * @param picBitmap
     * @param newWidth
     * @param newHeight
     */
    public static Bitmap resizeBitmap(Bitmap picBitmap, float newWidth, float newHeight) {
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / picBitmap.getWidth(), newHeight / picBitmap.getHeight());
        return Bitmap.createBitmap(picBitmap, 0, 0, picBitmap.getWidth(), picBitmap.getHeight(), matrix, true);
    }

    /**
     * 根据图片的不同尺寸进行压缩
     *
     * @param originalPath
     * @param compressPath
     */
    public static void doCompressBySize(String originalPath, String compressPath) {
        try {
            Bitmap bm = getSmallBitmap(originalPath);
            int degree = readPictureDegree(originalPath);
            if (degree != 0) {
                bm = rotaingBitmap(degree, bm);
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(compressPath));
            if (new File(originalPath).length() > 1024 * 1024) {            // >1M
                bm.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            } else if (new File(originalPath).length() > 1024 * 512) {        // >0.5M
                bm.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            } else if (new File(originalPath).length() > 1024 * 256) {        // > 0.25M
                bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            } else {
                bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int reqWidth = 720;
        int reqHeight = (reqWidth * options.outHeight) / options.outWidth;
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    // 计算图片的缩放值
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 读取一张图片的旋转角度属性
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将一张图片旋转一定的角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotateBitmap;
    }
}
