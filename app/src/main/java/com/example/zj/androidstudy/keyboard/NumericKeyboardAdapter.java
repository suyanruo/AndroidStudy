package com.example.zj.androidstudy.keyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zj.androidstudy.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义数字键盘
 */
public class NumericKeyboardAdapter extends RecyclerView.Adapter<NumericKeyboardAdapter.KeyboardViewHolder> {
    private Context mContext;
    protected List<String> mKeyLabelList;
    protected OnKeyboardClickListener mOnKeyboardClickListener;

    public NumericKeyboardAdapter(Context mContext, List<String> mKeyLabelList) {
        this.mContext = mContext;
        this.mKeyLabelList = mKeyLabelList;
    }

    @NonNull
    @Override
    public KeyboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_keyboard, parent, false);
        return new KeyboardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KeyboardViewHolder holder, final int position) {
        holder.refreshView(position);
    }

    @Override
    public int getItemCount() {
        return mKeyLabelList != null ? mKeyLabelList.size() : 0;
    }

    public class KeyboardViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;

        public KeyboardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }

        public void refreshView(final int position) {
            tvNumber.setText(mKeyLabelList.get(position));
            if (position == 9 || position == 11) {
                itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.c_regular_gray));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            }
            tvNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnKeyboardClickListener != null) {
                        mOnKeyboardClickListener.onKeyPressed(position);
                    }
                }
            });
        }
    }

    public void setOnKeyboardClickListener(OnKeyboardClickListener listener) {
        mOnKeyboardClickListener = listener;
    }

    public interface OnKeyboardClickListener {
        void onKeyPressed(int position);
    }
}
