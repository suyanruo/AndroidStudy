package com.example.zj.androidstudy.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.ScreenUtil;

public class KeyboardActivity extends AppCompatActivity {
    private static final String TAG = "KeyboardActivity";
    private EditText mEtKeyboard;
    private EditText mEtPopupKeyboard;
    private Button mBtnEnableDot;
    private NumericKeyboardView keyboard;
    private KeyboardHelper mKeyboardHelper;
    private boolean flag = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        mEtKeyboard = findViewById(R.id.et_keyboard);
        mBtnEnableDot = findViewById(R.id.btn_enable_dot);
        keyboard = findViewById(R.id.numeric_keyboard);

        mEtPopupKeyboard = findViewById(R.id.et_popup_keyboard);

        initNormalKeyboard();
        initPopupKeyboard();
    }

    private void initNormalKeyboard() {
        ScreenUtil.disableSysKeyboard(this, mEtKeyboard);
        keyboard.setOnKeyboardClickListener(new NumericKeyboardView.OnKeyboardListener() {

            @Override
            public void onKeyResult(String pressedKey) {
                Log.e(TAG, "pressedKey: " + pressedKey);
            }
        });
        keyboard.setEtInput(mEtKeyboard);
        mEtKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtKeyboard.setFocusable(true);
                mEtKeyboard.setFocusableInTouchMode(true);
                keyboard.show();
            }
        });
        mEtKeyboard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    keyboard.dismiss();
                }
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

    private void initPopupKeyboard() {
        mKeyboardHelper = new KeyboardHelper(this, mEtPopupKeyboard);
        ScreenUtil.disableSysKeyboard(this, mEtPopupKeyboard);
        mEtPopupKeyboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEtPopupKeyboard.setFocusable(true);
                mEtPopupKeyboard.setFocusableInTouchMode(true);
                mKeyboardHelper.showKeyboard();
                return false;
            }
        });
        mEtPopupKeyboard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mKeyboardHelper.hideKeyboard();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKeyboardHelper != null) {
            mKeyboardHelper.onDestroy();
        }
    }
}