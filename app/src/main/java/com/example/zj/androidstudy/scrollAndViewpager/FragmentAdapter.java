package com.example.zj.androidstudy.scrollAndViewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zj.androidstudy.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019-12-19.
 */
public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.TextHolder> {

  private Context context;
  private List<String> strs;
  private LayoutInflater inflater;

  public FragmentAdapter(Context context, List<String> strs) {
    this.context = context;
    this.strs = strs;
    inflater = LayoutInflater.from(context);
  }

  @Override
  public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.layout_item_fragment_adapter, parent, false);
    TextHolder holder = new TextHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(TextHolder holder, int position) {
    String str = strs.get(position);
    holder.textView.setText(str);
  }

  @Override
  public int getItemCount() {
    return strs.size();
  }

  public class TextHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public TextHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.tv_item);
    }

  }
}
