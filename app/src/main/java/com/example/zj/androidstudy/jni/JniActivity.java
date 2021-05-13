package com.example.zj.androidstudy.jni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.zj.androidstudy.R;

public class JniActivity extends AppCompatActivity {
    private static final String TAG = "JniActivity";

    static {
        System.loadLibrary("jni-test");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        TextView textView = findViewById(R.id.tv_jni_response);
        textView.setText(get());
    }

    public static void staticMethodCalledByJni(String s) {
        Log.e(TAG, "staticMethodCalledByJni: " + s);
    }

    public void methodCalledByJni(String s) {
        Log.e(TAG, "methodCalledByJni: " + s);
        Log.e(TAG, "methodCalledByJni: " + change());
    }

    public native void set(String s);
    public native String get();
    public native String change();
}