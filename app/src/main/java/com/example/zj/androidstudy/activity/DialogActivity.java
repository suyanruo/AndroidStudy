package com.example.zj.androidstudy.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.view.CustomDialog;
import com.example.zj.androidstudy.view.dialogAnimator.BaseEffects;
import com.example.zj.androidstudy.view.dialogAnimator.SlideBottom;

public class DialogActivity extends AppCompatActivity {

    private Button btnPhone;
    private Button btnShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        // 点击区域外消失
        setFinishOnTouchOutside(true);

        btnPhone = findViewById(R.id.btnPhone);
        btnShowDialog = findViewById(R.id.btn_show_dialog);

        btnPhone.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(DialogActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DialogActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, Constants.REQUEST_PERMISSION_CODE);
            } else {
                callPhone();
            }
        });
        btnShowDialog.setOnClickListener(v -> {
            showDialog2();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void callPhone() {
        Toast.makeText(getApplicationContext(), "已拨号", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:13866668888"));
        startActivity(intent);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_signin, null);
        AlertDialog alertDialog = builder.setView(view).create();
        alertDialog.setOnShowListener(dialog -> {
            BaseEffects type = new SlideBottom();
            type.start(view.getRootView());
        });
//        alertDialog.getWindow().setWindowAnimations(R.style.dialogStyle);
        alertDialog.show();
    }

    private void showDialog2() {
        CustomDialog dialog = new CustomDialog(this);
        dialog.show();
    }
}
