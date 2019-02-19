package com.example.zj.androidstudy.chapterNetwork;

import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HttpConnManager {

    private HttpURLConnection getHttpURLConnection(String path) {
        HttpURLConnection httpURLConnection = null;
        try {
            // url
            URL url = new URL(path);
            // connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            httpURLConnection.setConnectTimeout(15000);
            // 设置读取超时时间
            httpURLConnection.setReadTimeout(15000);
            // 添加Header
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 接收输入流
            httpURLConnection.setDoInput(true);
            // 传递参数时需要开启
            httpURLConnection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }

    // 通过输出流将http请求发送出去
    public void postParams(OutputStream os, List<NameValuePair> params) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (NameValuePair pair : params) {
            if (!TextUtils.isEmpty(stringBuilder)) {
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(pair.getName(), "UTF-8"))
                    .append("=").append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        writer.write(stringBuilder.toString());
        writer.flush();
        writer.close();
    }

    // 测试网址：http://ip.taobao.com/service/getIpInfo.php
    public void usePost(String url) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = getHttpURLConnection(url);
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("ip", "59.108.54.37"));
            postParams(httpURLConnection.getOutputStream(), params);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            int code = httpURLConnection.getResponseCode();
            String result = convertStreamToString(inputStream);
            Log.e("zj", "请求状态吗：" + code + "，结果：" + result);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
