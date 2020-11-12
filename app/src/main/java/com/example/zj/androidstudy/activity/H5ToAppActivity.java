package com.example.zj.androidstudy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.example.zj.androidstudy.R;

public class H5ToAppActivity extends AppCompatActivity {
  private String url;
  private WebView webview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_h5_to_app);

    final Button btnGoMain = findViewById(R.id.btn_go_main);
    btnGoMain.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        webview = findViewById(R.id.webviewh5);
        url = "file:///android_asset/goMain.html";

        WebSettings wSet = webview.getSettings();
        wSet.setJavaScriptEnabled(true);
        webview.loadUrl(url);
      }
    });

    findViewById(R.id.btn_move).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // scrollBy() scrollTo() 只改变控件内容位置，不改变控件位置
        btnGoMain.scrollBy(-100, 0);
      }
    });
  }
}
