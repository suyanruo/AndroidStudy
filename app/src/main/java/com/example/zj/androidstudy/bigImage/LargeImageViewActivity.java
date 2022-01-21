package com.example.zj.androidstudy.bigImage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.zj.androidstudy.R;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageViewActivity extends AppCompatActivity {
//    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);

//        mLargeImageView = findViewById(R.id.id_largetImageview);
//        try {
//            InputStream inputStream = getAssets().open("qm.jpg");
//            mLargeImageView.setInputStream(inputStream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        SubsamplingScaleImageView imageView = findViewById(R.id.imageView);
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_START);
        imageView.setOrientation(SubsamplingScaleImageView.ORIENTATION_90);
        imageView.setImage(ImageSource.asset("qm.jpg"));
    }
}