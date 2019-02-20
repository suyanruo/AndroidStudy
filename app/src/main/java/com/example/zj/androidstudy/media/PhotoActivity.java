package com.example.zj.androidstudy.media;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PhotoActivity extends BaseActivity {
    private ImageView mPictureImageView;
    private Uri mImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mPictureImageView = findViewById(R.id.iv_picture);

        findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        File file = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageUri = FileProvider.getUriForFile(this, "com.example.zj.androidstudy.fileprovider", file);
        } else {
            mImageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 1:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                    mPictureImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
