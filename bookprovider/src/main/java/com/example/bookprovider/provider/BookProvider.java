package com.example.bookprovider.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.bookprovider.helper.DbOpenHelper;

public class BookProvider extends ContentProvider {
  private static final String TAG = "BookProvider";
  public static final String AUTHORITY = "com.example.bookprovider";

  public static final int BOOK_URI_CODE = 0;
  public static final int USER_URI_CODE = 1;

  private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
    sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
  }

  private Context mContext;
  private SQLiteDatabase mSqLiteDatabase;

  @Override
  public boolean onCreate() {
    // 运行在UI线程
    Log.e(TAG, "onCreate. Current Thread is " + Thread.currentThread().getName());
    mContext = getContext();
    initProviderData();
    return true;
  }

  private void initProviderData() {
    mSqLiteDatabase = new DbOpenHelper(mContext).getWritableDatabase();
    mSqLiteDatabase.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
    mSqLiteDatabase.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
    mSqLiteDatabase.execSQL("insert into book values(3, 'Android');");
    mSqLiteDatabase.execSQL("insert into book values(4, 'ios');");
    mSqLiteDatabase.execSQL("insert into book values(5, 'Html');");
    mSqLiteDatabase.execSQL("insert into user values(1, 'Jake', 1);");
    mSqLiteDatabase.execSQL("insert into user values(2, 'Jasmine', 0);");
  }

  @Override
  public String getType(Uri uri) {
    return null;
//    return "*/*";
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    String tableName = getTableName(uri);
    if (tableName == null) {
      throw new IllegalArgumentException("Unsupported URI");
    }
    mSqLiteDatabase.insert(tableName, null, values);
    mContext.getContentResolver().notifyChange(uri, null);
    return uri;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    String tableName = getTableName(uri);
    if (tableName == null) {
      throw new IllegalArgumentException("Unsupported URI");
    }
    int count = mSqLiteDatabase.delete(tableName, selection, selectionArgs);
    if (count > 0) {
      mContext.getContentResolver().notifyChange(uri, null);
    }
    return count;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    String tableName = getTableName(uri);
    if (tableName == null) {
      throw new IllegalArgumentException("Unsupported URI");
    }
    int row = mSqLiteDatabase.update(tableName, values, selection, selectionArgs);
    if (row > 0) {
      mContext.getContentResolver().notifyChange(uri, null);
    }
    return row;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    // 除了onCreate方法其他方法均运行在Binder线程池
    Log.e(TAG, "query. Current Thread is " + Thread.currentThread().getName());
    String tableName = getTableName(uri);
    if (tableName == null) {
      throw new IllegalArgumentException("Unsupported URI");
    }
    return mSqLiteDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
  }

  private String getTableName(Uri uri) {
    switch (sUriMatcher.match(uri)) {
      case BOOK_URI_CODE:
        return DbOpenHelper.BOOK_TABLE_NAME;
      case USER_URI_CODE:
        return DbOpenHelper.USER_TABLE_NAME;
      default:
        return null;
    }
  }
}
