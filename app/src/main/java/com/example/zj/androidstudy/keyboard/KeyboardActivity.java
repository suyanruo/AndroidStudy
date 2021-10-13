package com.example.zj.androidstudy.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.ScreenUtil;

public class KeyboardActivity extends AppCompatActivity {
    private static final String TAG = "KeyboardActivity";
    private EditText mEtKeyboard;
    private Button mBtnEnableDot;
    private boolean flag = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        mEtKeyboard = findViewById(R.id.et_keyboard);
        mBtnEnableDot = findViewById(R.id.btn_enable_dot);

        ScreenUtil.disableSysKeyboard(this, mEtKeyboard);

        final NumericKeyboardView keyboard = findViewById(R.id.numeric_keyboard);
        keyboard.setOnKeyboardClickListener(new NumericKeyboardView.OnKeyboardListener() {

            @Override
            public void onKeyResult(String pressedKey) {
                Log.e(TAG, "pressedKey: " + pressedKey);
            }
        });
        keyboard.setEtInput(mEtKeyboard);
        keyboard.show();
        mEtKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtKeyboard.setFocusable(true);
                mEtKeyboard.setFocusableInTouchMode(true);
                keyboard.show();
            }
        });
        mBtnEnableDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                keyboard.changeDotAvailability(flag);
                mBtnEnableDot.setText(flag ? R.string.disable_dot : R.string.enable_dot);
            }
        });
    }
}