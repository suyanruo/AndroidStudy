package com.example.zj.androidstudy.tool;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.zj.androidstudy.storage.Video;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/8/18.
 */
public class StorageUtil {

  public static List<Video> getFileUri(Context context) {
    List<Video> videoList = new ArrayList<Video>();

    String[] projection = new String[] {
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE
    };
    String selection = MediaStore.Video.Media.DURATION + " >= ?";
    String[] selectionArgs = new String[] {
        String.valueOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES))
    };
    String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

    try (Cursor cursor = context.getContentResolver().query(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )) {
      // Cache column indices.
      int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
      int nameColumn =
          cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
      int durationColumn =
          cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
      int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

      while (cursor.moveToNext()) {
        // Get values of columns for a given video.
        long id = cursor.getLong(idColumn);
        String name = cursor.getString(nameColumn);
        int duration = cursor.getInt(durationColumn);
        int size = cursor.getInt(sizeColumn);

        Uri contentUri = ContentUris.withAppendedId(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

        // Stores column values and the contentUri in a local object
        // that represents the media file.
        videoList.add(new Video(contentUri, name, duration, size));
      }
    }
    return videoList;
  }

  public static void saveImage(Context context, Bitmap bitmap) {
    ContentValues values = new ContentValues();
    Uri insertUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    try {
      OutputStream outputStream = context.getContentResolver().openOutputStream(insertUri);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
