package com.example.zj.androidstudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.view.expandLayout.ExpandableLayout;

public class CustomViewActivity extends AppCompatActivity {
  ExpandableLayout expandableLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_view);

    expandableLayout = findViewById(R.id.elv_expand);

    findViewById(R.id.elv_expand).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        CustomViewActivity.this.changeExpandViewStatus();
      }
    });
  }

  private void changeExpandViewStatus() {
    expandableLayout.toggle(true);
  }
}
