package com.example.zj.androidstudy.keyboard;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.ScreenUtil;

public class KeyboardHelper {
    private Activity mActivity;
    private Window mWindow;
    private View mDecorView;
    private View mContentView;
    private NumericKeyboardView mKeyboardView;
    private TextView mTextView;
    private PopupWindow mKeyboardWindow;

    private int mScrollDistance = 50; // 输入框在键盘被弹出时，要被推上去的距离
    private int mKeyboardHeight = -1;//键盘高度
    private int mScreenHeightNonavbar = -1; // 不包含导航栏的高度
    private int mBottomStatusHeight = 0;//底部虚拟按键高度

    public KeyboardHelper(Activity activity, TextView textView) {
        this.mActivity = activity;
        this.mTextView = textView;

        initSize();
        initWindow();
        initKeyboard();
    }

    private void initSize() {
        mScreenHeightNonavbar = ScreenUtil.getScreenHeight(mActivity);
        mBottomStatusHeight = ScreenUtil.getNavigationBarHeight(mActivity);
    }

    private void initWindow() {
        this.mWindow = mActivity.getWindow();
        this.mDecorView = this.mWindow.getDecorView();
        this.mContentView = this.mWindow.findViewById(Window.ID_ANDROID_CONTENT);
    }

    private void initKeyboard() {
        mKeyboardView = (NumericKeyboardView) LayoutInflater.from(mActivity).inflate(R.layout.view_numeric_keyboard, null);
        mKeyboardHeight = getKeyboardHeight();
        mKeyboardView.setEtInput((EditText) mTextView);
        mKeyboardView.setOnCloseListener(new NumericKeyboardView.onCloseListener() {
            @Override
            public void onClose() {
                hideKeyboard();
            }
        });
        mKeyboardWindow = new PopupWindow(mKeyboardView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mKeyboardWindow.setAnimationStyle(R.style.IfopKbAnimationFade);
        // 设置背景为透明
        ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mKeyboardWindow.setBackgroundDrawable(drawable);
        mKeyboardWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mKeyboardWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mScrollDistance > 0) {
                    int dis = mScrollDistance;
                    mScrollDistance = 0;
                    if (mContentView != null) {
                        mContentView.scrollBy(0, -dis);
                    }
                }
            }
        });
    }

    public void showKeyboard() {
        ScreenUtil.disableSysKeyboard(mActivity, mTextView);
        if (mKeyboardWindow != null) {
            if (!mKeyboardWindow.isShowing()) {
                mKeyboardWindow.showAtLocation(mDecorView, Gravity.BOTTOM, 0, 0);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    mKeyboardWindow.update();
                }
                mScrollDistance = calculateScrollDistance();
                //需要移动的距离mScrollDistance，数字键盘若不遮挡输入框mScrollDistance<0，则不需要移动
                if (mDecorView != null && mContentView != null) {
                    if (mScrollDistance > 0) {
                        mContentView.scrollBy(0, mScrollDistance);
                    }
                }
            }
        }
    }

    public void hideKeyboard() {
        if (null != mKeyboardWindow) {
            if (mKeyboardWindow.isShowing()) {
                mKeyboardWindow.dismiss();
            }
        }
    }

    //计算需要移动的距离
    private int calculateScrollDistance() {
        int[] pos = new int[2];
        //pos[1]为输入框顶端到屏幕顶端的距离
        mTextView.getLocationOnScreen(pos);
        //输入框底端到屏幕顶端的距离 = 输入框顶端到屏幕顶端的距离 + 输入框高度
        int textViewBottom = pos[1] + mTextView.getHeight();
        //需要移动的距离 = 输入框底端到屏幕顶端的距离 + 键盘高度 - 屏幕高度
        // （若大于0，说明超出了屏幕的承载距离，需要移动）
        int scroll = textViewBottom + mKeyboardHeight - mScreenHeightNonavbar;
        String brand = Build.BRAND;
        if (brand.equals("Meizu") && ScreenUtil.hasSmartBar()) {
            scroll += mBottomStatusHeight;
        }
        return scroll;
    }

    private int getKeyboardHeight() {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mKeyboardView.measure(width, height);
        return mKeyboardView.getMeasuredHeight();
    }

    public void onDestroy() {
        hideKeyboard();
        mKeyboardWindow = null;
        mKeyboardView = null;
        mDecorView = null;
        mContentView = null;
        mWindow = null;
    }
}
