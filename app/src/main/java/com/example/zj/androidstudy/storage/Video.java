package com.example.zj.androidstudy.storage;

import android.net.Uri;

/**
 * Created on 2020/8/18.
 */
public class Video {
  private final Uri uri;
  private final String name;
  private final int duration;
  private final int size;

  public Video(Uri uri, String name, int duration, int size) {
    this.uri = uri;
    this.name = name;
    this.duration = duration;
    this.size = size;
  }
}
