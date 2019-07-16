package com.example.zj.androidstudy.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zj.androidstudy.R;

public class DialogActivity extends AppCompatActivity {

  Button btnPhone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog);

    // 点击区域外消失
    setFinishOnTouchOutside(true);

    btnPhone = findViewById(R.id.btnPhone);

    btnPhone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "已拨号", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:13866668888"));
        startActivity(intent);
      }
    });
  }
}
