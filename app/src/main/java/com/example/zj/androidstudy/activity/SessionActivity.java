package com.example.zj.androidstudy.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;
import com.example.zj.androidstudy.tool.KeyboardChangeListener;

public class SessionActivity extends BaseActivity implements KeyboardChangeListener.KeyBoardListener {
    private Button mBtnShowSession;
    private LinearLayout mLlSession;
    private EditText mEtSession;
    private KeyboardChangeListener mKeyboardChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        initView();
    }

    private void initView() {
        // 设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FrameLayout rootView = findViewById(android.R.id.content);
        LayoutInflater inflater = LayoutInflater.from(this);
        FrameLayout child = (FrameLayout) rootView.getChildAt(0);
        View functionView = inflater.inflate(R.layout.view_function, null);
        FrameLayout.LayoutParams functionParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        );
//        functionParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        functionView.setLayoutParams(functionParams);
        child.addView(functionView);

        mBtnShowSession = findViewById(R.id.btn_barrage);
        mLlSession = findViewById(R.id.ll_session);
        mEtSession = findViewById(R.id.et_session);

        mKeyboardChangeListener = new KeyboardChangeListener(this);
        mKeyboardChangeListener.setKeyBoardListener(this);

        mBtnShowSession.setOnClickListener(v -> {
            mKeyboardChangeListener.registerListener();
            mLlSession.setVisibility(View.VISIBLE);
            showSoftInput(mEtSession);
        });
        mEtSession.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (!isShow) {
            mLlSession.setVisibility(View.GONE);
            mKeyboardChangeListener.unregisterListener();
        }
    }
}