package com.example.zj.androidstudy.activity;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.contentProvider.ContentActivity;
import com.example.zj.androidstudy.media.PhotoActivity;
import com.example.zj.androidstudy.tool.NotificationUtil;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new HttpClientManager().usePost("http://ip.taobao.com/service/getIpInfo.php");
//                new HttpConnManager().usePost("http://ip.taobao.com/service/getIpInfo.php");
//            }
//        }).start();
        findViewById(R.id.btn_binder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BinderActivity.class));
            }
        });

        findViewById(R.id.btn_material_design).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MaterialDesignActivity.class));
            }
        });

        findViewById(R.id.btn_content_provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ContentActivity.class));
                NotificationUtil.showNotification(MainActivity.this, new Intent(MainActivity.this, ContentActivity.class));
            }
        });

        findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhotoActivity.class));
            }
        });
    }
}
