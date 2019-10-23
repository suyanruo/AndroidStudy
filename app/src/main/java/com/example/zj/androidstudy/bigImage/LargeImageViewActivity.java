package com.example.zj.androidstudy.bigImage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zj.androidstudy.R;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageViewActivity extends AppCompatActivity
{
    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image_view);

        mLargeImageView = findViewById(R.id.id_largetImageview);
        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            mLargeImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}