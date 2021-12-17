package com.example.zj.androidstudy.cutOut;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.zj.androidstudy.R;

public class CutOutActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_cut_out);

    View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      // 设置刘海屏。亦可通过style设置activity
      WindowManager.LayoutParams params = getWindow().getAttributes();
      params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
      getWindow().setAttributes(params);
    }

    ConstraintLayout rootLayout = findViewById(R.id.root_cut_out);
    final Button btnTop = findViewById(R.id.btn_top);
    final Button btnLeft = findViewById(R.id.btn_left);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      rootLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
        @Override
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
          DisplayCutout cutout = insets.getDisplayCutout();
          if (cutout != null) {
            int left = cutout.getSafeInsetLeft();
            int top = cutout.getSafeInsetTop();
            int right = cutout.getSafeInsetRight();
            int bottom = cutout.getSafeInsetBottom();

            ConstraintLayout.LayoutParams topButtonParams = (ConstraintLayout.LayoutParams) btnTop.getLayoutParams();
            topButtonParams.setMargins(left, top, right, bottom);

            ConstraintLayout.LayoutParams leftButtonParams = (ConstraintLayout.LayoutParams) btnLeft.getLayoutParams();
            leftButtonParams.setMargins(left, top, right, bottom);
          }
          return insets.consumeSystemWindowInsets();
        }
      });
    }
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }
}
