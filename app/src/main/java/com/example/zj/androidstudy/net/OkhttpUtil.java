package com.example.zj.androidstudy.net;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import androidx.annotation.StringDef;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created on 2020-04-27.
 * ref: https://www.jianshu.com/p/af144d662bfd
 */
public class OkhttpUtil {
  private static final String TAG = "OkhttpUtil";
  public static final int HTTP_TIME_OUT = 3000;

  private OkHttpClient okHttpClient;

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({})
  public @interface RequestType {
    String GET = "GET";
    String POST = "POST";
    String PUT = "PUT";
  }

  private OkhttpUtil() {
    okHttpClient = buildClient();
  }

  private OkHttpClient buildClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .readTimeout(HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
        .writeTimeout(HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
        .connectTimeout(HTTP_TIME_OUT, TimeUnit.MILLISECONDS);

    return builder.build();
  }

  public Request buildGetRequest(String url) {
    Request request = new Request.Builder()
        .url(url)
        .get()
        .addHeader("", "")
        .tag("")  // 为当前request请求增加tag，可在okHttpClient中使用tag获取到当前请求
        .build();
    return request;
  }

  public Request buildPostRequest(String content, String url) {
    // 创建请求body，MediaType请求包体类型
    RequestBody requestBody = RequestBody.create(MediaType.parse("text/html; charset=utf-8"), content);
    Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .addHeader("name", "value")
        .tag("postSync")
        .build();
    return request;
  }

  /**
   * 提交普通字符串
   */
  public RequestBody buildRequestBodyFromString(String content) {
    return RequestBody.create(MediaType.parse("text/html; charset=utf-8"), content);
  }

  public RequestBody buildRequestBodyFromJson(String json) {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
  }

  public RequestBody buildRequestBodyFromFile(File file) {
//    return new RequestBody() {
//      @Override
//      public MediaType contentType() {
//        return MediaType.parse("application/octet-stream; charset=utf-8");
//      }
//
//      @Override
//      public void writeTo(BufferedSink sink) throws IOException {
//        sink.writeUtf8("字符串作为流提交");
//        // 重写此处可以实现文件上传进度检测
//      }
//    };

    return RequestBody.create(MediaType.parse("application/octet-stream; charset=utf-8"), file);
  }

  public RequestBody buildRequestBodyFromForm(Map<String, String> form) {
    FormBody.Builder builder = new FormBody.Builder();
    Iterator iterator = form.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
      builder.add(entry.getKey(), entry.getValue());
    }
    return builder.build();
  }

  public RequestBody buildMultiRequestBoby(Map<String, String> form, File file) {
    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream; charset=utf-8"), file);

    // 方式一
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    Set<Map.Entry<String, String>> entries = form.entrySet();
    for (Map.Entry<String, String> entry : entries) {
      builder.addFormDataPart(entry.getKey(), entry.getValue());
    }
    return builder.addFormDataPart("file", file.getName(), fileBody).build();

    /*
    // 方式二
    FormBody formBody = new FormBody.Builder()
        .add("key1", "value1")
        .add("key2", "value2")
        .add("key3", "value3")
        .build();
    RequestBody requestBody1 = new MultipartBody.Builder()
        .addPart(Headers.of(
            "Content-Disposition",
            "form-data; name=\"params\""),
            formBody)
        .addPart(Headers.of(
            "Content-Disposition",
            "form-data; name=\"file\"; filename=\"plans.xml\""),
            fileBody)
        .build();
    */
  }

  public void sendGetRequest(String url) {
    synchCall(okHttpClient.newCall(buildGetRequest(url)));
  }

  public void synchCall(Call call) {
    try {
      Response response = call.execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void asynchCall(Call call) {
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        Log.i(TAG, "onFailure");
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        int code = response.code();
        if (code == 200) {
          String result = response.body().string();
          Log.i(TAG, result);
        } else {
          Log.e(TAG, response.message());
        }
      }
    });
  }

  public void cancelRequest(Object tag) {
    if (okHttpClient == null || tag == null) {
      return;
    }
    for (Call call : okHttpClient.dispatcher().queuedCalls()) {
      if (tag.equals(call.request().tag())) {
        call.cancel();
      }
    }
    for (Call call : okHttpClient.dispatcher().runningCalls()) {
      if (tag.equals(call.request().tag())) {
        call.cancel();
      }
    }
  }

  public void cancelAllRequest() {
    if (okHttpClient == null) {
      return;
    }
    for (Call call : okHttpClient.dispatcher().queuedCalls()) {
      call.cancel();
    }
    for (Call call : okHttpClient.dispatcher().runningCalls()) {
      call.cancel();
    }
  }

  static class OkhttpHelper {
    static final OkhttpUtil instance = new OkhttpUtil();

    public static OkhttpUtil getInstance() {
      return instance;
    }
  }
}
