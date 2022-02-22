package com.example.zj.androidstudy.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void hideSoftInput() {
        if (this.getCurrentFocus() != null &&
                this.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showSoftInput(View view) {
        if (view.requestFocus()) {
            inputMethodManager =
                    (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        // 监听Home键可以通过onUserLeaveHint()方法来实现
        super.onUserLeaveHint();
//        Toast.makeText(this, "程序已退到后台", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Toast.makeText(this, "菜单", Toast.LENGTH_SHORT).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
