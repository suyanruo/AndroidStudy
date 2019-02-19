package com.example.zj.androidstudy.chapterNetwork;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientManager {

    // 创建HttpClient
    private HttpClient createHttpClient() {
        HttpParams defaultHttpParams = new BasicHttpParams();
        // 设置连接超时时间
        HttpConnectionParams.setConnectionTimeout(defaultHttpParams, 15000);
        // 设置请求超时时间
        HttpConnectionParams.setSoTimeout(defaultHttpParams, 15000);
        // 设置协议版本
        HttpProtocolParams.setVersion(defaultHttpParams, HttpVersion.HTTP_1_1);
        // 设置编码格式
        HttpProtocolParams.setContentCharset(defaultHttpParams, HTTP.UTF_8);
        // 持续握手
        HttpProtocolParams.setUseExpectContinue(defaultHttpParams, true);

        return new DefaultHttpClient(defaultHttpParams);
    }

    public void useGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient httpClient = createHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            int code = httpResponse.getStatusLine().getStatusCode();
            if (null != httpEntity) {
                InputStream inputStream = httpEntity.getContent();
                String result = convertStreamToString(inputStream);
                Log.e("zj", "请求状态吗：" + code + "，结果：" + result);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 测试网址：http://ip.taobao.com/service/getIpInfo.php
    public void usePost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Connection", "Keep-Alive");
        try {
            HttpClient httpClient = createHttpClient();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("ip", "59.108.54.37"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            int code = httpResponse.getStatusLine().getStatusCode();
            if (null != httpEntity) {
                InputStream is = httpEntity.getContent();
                String result = convertStreamToString(is);
                Log.e("zj", "请求状态吗：" + code + "，结果：" + result);
                is.close();
            }
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
