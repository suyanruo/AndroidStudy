package com.example.zj.androidstudy.view.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created on 2019-06-20.
 */
public abstract class GridDecoration extends RecyclerView.ItemDecoration {
  private Paint mPaint;
  private int lineWidth;//px 分割线宽
  private int spanCount;
  private int itemSize;

  public GridDecoration(Context context, float lineWidthDp, @ColorInt int colorRGB, int spanCount, int itemSize) {
//    this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, context.getResources().getDisplayMetrics());
    this.lineWidth = (int) lineWidthDp;
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(colorRGB);
    mPaint.setStyle(Paint.Style.FILL);
    this.spanCount = spanCount;
    this.itemSize = itemSize;
  }

  public GridDecoration(Context context, int lineWidthDp, @ColorInt int colorRGB, int spanCount, int itemSize) {
    this(context, (float) lineWidthDp, colorRGB, spanCount, itemSize);
  }

  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    //left, top, right, bottom
    int childCount1 = parent.getChildCount();
    for (int i = 0; i < childCount1; i++) {
      View child = parent.getChildAt(i);

      int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();

//      boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);
      boolean[] sideOffsetBooleans = getItemSide(itemPosition);

      if (sideOffsetBooleans[0]) {
        drawChildLeftVertical(child, c, parent);
      }
      if (sideOffsetBooleans[1]) {
        drawChildTopHorizontal(child, c, parent);
      }
      if (sideOffsetBooleans[2]) {
        drawChildRightVertical(child, c, parent);
      }
      if (sideOffsetBooleans[3]) {
        drawChildBottomHorizontal(child, c, parent);
      }
    }
  }

  private boolean[] getItemSide(int itemPosition) {
    // 顺序:left, top, right, bottom
    boolean[] booleans = {true, true, true, true};
//    if (itemPosition / spanCount == 0) {
//      // 第一行
//      booleans[1] = false;
//      if (itemPosition == 0) {
//        // 第一个item
//        booleans[0] = false;
//        if (itemSize / spanCount == 0) {
//          booleans[3] = false;
//        }
//      } else if (itemPosition == spanCount - 1) {
//        // 第一行最后一个item
//        booleans[2] = false;
//        if (itemSize / spanCount == 0) {
//          booleans[3] = false;
//        }
//      } else {
//        if (itemSize / spanCount == 0) {
//          booleans[3] = false;
//        }
//      }
//    } else if (itemPosition / spanCount == itemSize / spanCount) {
//      // 最后一行
//      booleans[3] = false;
//      if (itemPosition % spanCount == 0) {
//        // 第一个显示右边距和上边距
//        booleans[0] = false;
//      } else if (itemPosition % spanCount == spanCount - 1) {
//        // 最后一个只显示左边距和上边距
//        booleans[2] = false;
//      }
//    } else if (itemPosition % spanCount == 0) {
//      // 每一行第一个显示右边距和下边距
//      booleans[0] = false;
//    } else if (itemPosition % spanCount == spanCount - 1) {
//      // 每一行第二个只显示左边距和下边距
//      booleans[2] = false;
//    }
    return booleans;
  }

  private void drawChildBottomHorizontal(View child, Canvas c, RecyclerView parent) {
    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
        .getLayoutParams();
    int left = child.getLeft() - params.leftMargin - lineWidth;
    int right = child.getRight() + params.rightMargin + lineWidth;
    int top = child.getBottom() + params.bottomMargin;
    int bottom = top + lineWidth;

    c.drawRect(left, top, right, bottom, mPaint);
  }

  private void drawChildTopHorizontal(View child, Canvas c, RecyclerView parent) {
    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
        .getLayoutParams();
    int left = child.getLeft() - params.leftMargin - lineWidth;
    int right = child.getRight() + params.rightMargin + lineWidth;
    int bottom = child.getTop() - params.topMargin;
    int top = bottom - lineWidth;

    c.drawRect(left, top, right, bottom, mPaint);
  }

  private void drawChildLeftVertical(View child, Canvas c, RecyclerView parent) {
    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
        .getLayoutParams();
    int top = child.getTop() - params.topMargin - lineWidth;
    int bottom = child.getBottom() + params.bottomMargin + lineWidth;
    int right = child.getLeft() - params.leftMargin;
    int left = right - lineWidth;

    c.drawRect(left, top, right, bottom, mPaint);
  }

  private void drawChildRightVertical(View child, Canvas c, RecyclerView parent) {
    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
        .getLayoutParams();
    int top = child.getTop() - params.topMargin - lineWidth;
    int bottom = child.getBottom() + params.bottomMargin + lineWidth;
    int left = child.getRight() + params.rightMargin;
    int right = left + lineWidth;

    c.drawRect(left, top, right, bottom, mPaint);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
    //作为传递left,right,top,bottom的偏移值来用的
    int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//    boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);
    boolean[] sideOffsetBooleans = getItemSide(itemPosition);

    //如果是设置左边或者右边的边距，就只设置成指定宽度的一半，
    // 因为这个项目中的 Grid 是一行二列，如果不除以二的话，那么中间的间距就会很宽，
    //可根据实际项目需要修改成合适的值
    int left = sideOffsetBooleans[0] ? lineWidth / 2 : 0;
    int top = sideOffsetBooleans[1] ? lineWidth / 2 : 0;
    int right = sideOffsetBooleans[2] ? lineWidth / 2 : 0;
    int bottom = sideOffsetBooleans[3] ? lineWidth / 2 : 0;

    outRect.set(left, top, right, bottom);
  }

  /**
   * 顺序:left, top, right, bottom
   *
   * @return boolean[4]
   */
  public abstract boolean[] getItemSidesIsHaveOffsets(int itemPosition);
}
