package com.example.zj.androidstudy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zj.androidstudy.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 4/11/21.
 */

public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewPagerHolder> {
  private Context mContext;
  private List<Integer> mList;

  public Fragment2Adapter(Context context, List<Integer> list) {
    this.mContext = context;
    this.mList = list;
  }

  @NonNull
  @Override
  public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_view_pager2, parent, false);
    return new ViewPagerHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
    holder.bindData(mList.get(position));
  }

  @Override
  public int getItemCount() {
    return mList != null ? mList.size() : 0;
  }

  public static class ViewPagerHolder extends RecyclerView.ViewHolder {
    private TextView tvContent;
    private String[] colors = new String[]{"#CCFF99","#41F1E5","#8D41F1","#FF99CC"};
    public ViewPagerHolder(@NonNull View itemView) {
      super(itemView);
      tvContent = itemView.findViewById(R.id.tv_content);
    }

    public void bindData(int pos) {
      tvContent.setBackgroundColor(Color.parseColor(colors[pos]));
      tvContent.setText(String.valueOf(pos));
    }
  }
}
