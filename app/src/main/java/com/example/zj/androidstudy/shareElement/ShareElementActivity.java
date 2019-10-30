package com.example.zj.androidstudy.shareElement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.zj.androidstudy.R;

public class ShareElementActivity extends AppCompatActivity {
  private ImageView ivShareElement;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_share_element);

    ivShareElement = findViewById(R.id.iv_share_element);
    ivShareElement.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
          Intent intent = new Intent(ShareElementActivity.this, ShareElement2Activity.class);
          ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
              ShareElementActivity.this, ivShareElement, getString(R.string.share_element_img));
          startActivity(intent, options.toBundle());
        }
      }
    });
  }
}
