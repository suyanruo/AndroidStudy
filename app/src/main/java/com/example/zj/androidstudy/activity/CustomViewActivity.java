package com.example.zj.androidstudy.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.view.SizeChangeCircleView;
import com.example.zj.androidstudy.view.expandLayout.ExpandableLayout;

public class CustomViewActivity extends AppCompatActivity {
  ExpandableLayout expandableLayout;
  private SizeChangeCircleView circleView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_view);

    expandableLayout = findViewById(R.id.elv_expand);
    circleView = findViewById(R.id.sccv_water);

    expandableLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        changeExpandViewStatus();
      }
    });
    circleView.start();
  }

  private void changeExpandViewStatus() {
    expandableLayout.toggle(true);
  }
}
