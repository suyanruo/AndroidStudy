package com.example.zj.androidstudy.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.zj.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NumericKeyboardView extends RelativeLayout {
    private ImageView mIvBack;
    private RecyclerView mRvKey;
    private NumericKeyboardAdapter mAdapter;
    private EditText mEtInput;
    private Animation mAnimationIn;
    private Animation mAnimationOut;
    private List<String> mDataList = new ArrayList<>();
    private boolean dotAvailability;
    private OnKeyboardListener mOnKeyboardListener;

    public NumericKeyboardView(Context context) {
        this(context, null);
    }

    public NumericKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumericKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumericKeyboard);
        dotAvailability = typedArray.getBoolean(R.styleable.NumericKeyboard_dot_availability, true);
        typedArray.recycle();
        LayoutInflater.from(context).inflate(R.layout.layout_numberic_keyboard, this);
        mIvBack = findViewById(R.id.iv_keyboard_back);
        mRvKey = findViewById(R.id.rv_keyboard);

        initData();
        initView();
        initAnimation();
    }

    // 填充数据
    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 12; i++) {
            if (i < 9) {
                mDataList.add(String.valueOf(i + 1));
            } else if (i == 9) {
                if (dotAvailability) {
                    mDataList.add(".");
                } else {
                    mDataList.add("");
                }
            } else if (i == 10) {
                mDataList.add("0");
            } else {
                mDataList.add("<");
            }
        }
    }

    // 设置适配器
    private void initView() {
        mIvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) { // 点击关闭键盘
                dismiss();
            }
        });

        mRvKey.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new NumericKeyboardAdapter(getContext(), mDataList);
        mRvKey.setAdapter(mAdapter);
        GridItemDecoration divider = new GridItemDecoration.Builder(getContext())
                .setHorizontalSpan(1f)
                .setVerticalSpan(1f)
                .setColorResource(R.color.c_regular_gray)
                .build();
        mRvKey.addItemDecoration(divider);
        mAdapter.setOnKeyboardClickListener(new NumericKeyboardAdapter.OnKeyboardClickListener() {
            @Override
            public void onKeyPressed(int position) {
                onKeyClick(position);
                mOnKeyboardListener.onKeyResult(mDataList.get(position));
            }
        });
        setVisibility(GONE);
    }

    // 初始化动画效果
    private void initAnimation() {
        mAnimationIn = AnimationUtils.loadAnimation(getContext(), R.anim.anim_push_bottom_in);
        mAnimationOut = AnimationUtils.loadAnimation(getContext(), R.anim.anim_push_bottom_out);
    }

    // 弹出软键盘
    public void show() {
        if (!isVisible()) {
            startAnimation(mAnimationIn);
            setVisibility(VISIBLE);
        }
    }

    // 关闭软键盘
    public void dismiss() {
        if (isVisible()) {
            startAnimation(mAnimationOut);
            setVisibility(GONE);
        }
    }

    // 判断软键盘的状态
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    public void setOnKeyboardClickListener(final OnKeyboardListener listener) {
        mOnKeyboardListener = listener;
    }

    public void setEtInput(EditText mEtInput) {
        this.mEtInput = mEtInput;
    }

    public void changeDotAvailability(boolean dotAvailability) {
        this.dotAvailability = dotAvailability;
        initData();
        mAdapter.notifyDataSetChanged();
    }

    private void onKeyClick(int position) {
        int selection = mEtInput.getSelectionStart();
        StringBuilder result = new StringBuilder(mEtInput.getText().toString());
        switch (position) {
            case 9: // 按下小数点
                if (dotAvailability) {
                    if (result.lastIndexOf(mDataList.get(position)) == -1) {
                        if (result.length() == 0) {
                            result.append("0");
                            selection++;
                        }
                        result.insert(selection, mDataList.get(position));
                        selection++;
                        int dotIndex = result.lastIndexOf(mDataList.get(position));
                        int len = result.length();
                        if (dotIndex < len - 3) {
                            result.delete(dotIndex + 3, len);
                        }
                    }
                }
                break;
            case 11:
                if (selection > 0) {
                    result.deleteCharAt(--selection);
                }
                break;
            default: // 按下数字键
                int len = result.length();
                if (len == 0) {
                    result.append(mDataList.get(position));
                    selection++;
                } else if (len == 1) {
                    if (result.lastIndexOf("0") == 0) {
                        result.deleteCharAt(0);
                        selection--;
                    }
                    result.insert(selection, mDataList.get(position));
                    selection++;
                } else {
                    int dotIndex = result.lastIndexOf(".");
                    if (dotIndex >= len - 2 || dotIndex < 0 || selection <= dotIndex) {
                        result.insert(selection, mDataList.get(position));
                        selection++;
                    }
                }
                break;
        }
        final int selectIndex = selection;
        final String sResult = result.toString();
//        mEtInput.post(new Runnable() {
//            @Override
//            public void run() {
                mEtInput.setText(sResult);
                mEtInput.setSelection(selectIndex);
//            }
//        });
    }

    public interface OnKeyboardListener {
        void onKeyResult(String pressedKey);
    }
}
