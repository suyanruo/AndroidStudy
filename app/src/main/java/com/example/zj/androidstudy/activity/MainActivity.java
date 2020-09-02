package com.example.zj.androidstudy.activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.palette.graphics.Palette;

import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.aspectj.TimeSpend;
import com.example.zj.androidstudy.autoFillCode.AutoFillSmsCodeActivity;
import com.example.zj.androidstudy.baidu.MapActivity;
import com.example.zj.androidstudy.bigImage.LargeImageViewActivity;
import com.example.zj.androidstudy.camera.CameraActivity;
import com.example.zj.androidstudy.contentProvider.ContentActivity;
import com.example.zj.androidstudy.cutOut.CutOutActivity;
import com.example.zj.androidstudy.firebase.FirebaseActivity;
import com.example.zj.androidstudy.markdown.MarkdownActivity;
import com.example.zj.androidstudy.material.MaterialDesignActivity;
import com.example.zj.androidstudy.media.PhotoActivity;
import com.example.zj.androidstudy.net.OkhttpActivity;
import com.example.zj.androidstudy.pdf.PdfSearchActivity;
import com.example.zj.androidstudy.puzzle.PuzzleActivity;
import com.example.zj.androidstudy.scrollAndViewpager.ScrollViewPagerActivity;
import com.example.zj.androidstudy.service.ServiceActivity;
import com.example.zj.androidstudy.shareElement.ShareElementActivity;
import com.example.zj.androidstudy.thread.ThreadActivity;
import com.example.zj.androidstudy.tool.NotificationUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPalette();
        fetchIntentData();

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

        findViewById(R.id.btn_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
            }
        });

        findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        findViewById(R.id.btn_load_big_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LargeImageViewActivity.class));
            }
        });
        findViewById(R.id.btn_puzzle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PuzzleActivity.class));
            }
        });
        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
        findViewById(R.id.btn_custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
            }
        });
        findViewById(R.id.btn_activity_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, DialogActivity.class));
            }
        });
        findViewById(R.id.btn_activity_constraint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ConstraintActivity.class));
            }
        });

        findViewById(R.id.btn_activity_viewpager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
            }
        });

        findViewById(R.id.btn_activity_auto_fill_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              MainActivity.this.startActivity(new Intent(MainActivity.this, AutoFillSmsCodeActivity.class));
            }
          });
        findViewById(R.id.btn_activity_share_element).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ShareElementActivity.class));
            }
        });
        findViewById(R.id.btn_activity_cut_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CutOutActivity.class));
            }
        });
        findViewById(R.id.btn_activity_scroll_viewpager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ScrollViewPagerActivity.class));
            }
        });
        findViewById(R.id.btn_activity_pdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, PdfSearchActivity.class));
            }
        });
        findViewById(R.id.btn_activity_markdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MarkdownActivity.class));
            }
        });
        findViewById(R.id.btn_activity_firebase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, FirebaseActivity.class));
            }
        });
        findViewById(R.id.btn_activity_okhttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, OkhttpActivity.class));
            }
        });
        findViewById(R.id.btn_activity_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, ThreadActivity.class));

                startWhatsApp();
            }
        });
        findViewById(R.id.btn_activity_h5_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, H5ToAppActivity.class));
            }
        });
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.setStatusBarColor(vibrant.getRgb());
                }
            }
        });

    }

    private void startWhatsApp() {
        Uri uri = Uri.parse("whatsapp://send?phone=8618709251910");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
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

//    ViewStub vsRateUs;
//    private View inflateView;
//
//    //fragment在onDetach之后重新attach，ViewStub需要重新inflate一次View才能正常展示
//    if (vsRateUs.getParent() != null) {
//        initRateUs();
//    } else if (inflateView != null) {
//        inflateView.setVisibility(View.VISIBLE);
//    }
}
