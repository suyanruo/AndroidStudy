package com.example.zj.androidstudy.activity;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;

import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.aspectj.TimeSpend;
import com.example.zj.androidstudy.base.BaseActivity;
import com.example.zj.androidstudy.fragment.HomeFragment;
import com.example.zj.androidstudy.tool.EncryptionUtil;
import com.example.zj.androidstudy.tool.SharedPreferenceUtil;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPalette();
        fetchIntentData();
        requestPermission();

        initPassword();

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.main_content, new HomeFragment());
//        transaction.commitAllowingStateLoss();



//        if (ScreenUtil.hasNotchInScreen(this)) {
//            Toast.makeText(this, "刘海屏", Toast.LENGTH_SHORT).show();
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new HttpClientManager().usePost("http://ip.taobao.com/service/getIpInfo.php");
//                new HttpConnManager().usePost("http://ip.taobao.com/service/getIpInfo.php");
//            }
//        }).start();

    }

    /**
     * 设置actionBar颜色
     */
    @TimeSpend("SetPalette")
    private void showPalette() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_back);
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                // 通过Palette获取相应的色调
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                // 将颜色设置给对应的组件
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(vibrant.getRgb()));
                Window window = getWindow();
                window.setStatusBarColor(vibrant.getRgb());
            }
        });

    }

    private void fetchIntentData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            String str = Uri.decode(uri.getEncodedPath()) + "\n" + intent.getDataString();
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求授权
     */
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }
    }

    private void initPassword() {
        // 设置用户登录密码为"111111"
        String password = EncryptionUtil.INSTANCE.get3DesString("111111");
        Log.e(TAG, "password: " + password);
        SharedPreferenceUtil.saveString(this, Constants.USER_PASSWORD, password);
    }

}
