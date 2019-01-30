package com.example.zj.androidstudy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Scroller;

public class CustomSlideView extends View {
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;

    public CustomSlideView(Context context) {
        super(context);
    }

    public CustomSlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public CustomSlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 提供给外界调用，实现平缓平移效果
     * @param desX
     * @param desY
     */
    public void smoothScroll(int desX, int desY) {
        int scrollX = getScrollX();
        int deltaX = desX - scrollX;
        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        // 每次执行draw方法时会调用这个方法
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算移动的距离
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                Log.e("zj", "left: " + getLeft());
                // 设置移动view的方式
                // 方法一：使用layout方法
//                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                // 方法二：使用offsetLeftAndRight和offsetTopAndBottom方法
                offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);
                break;
        }
        return true;
    }
}
