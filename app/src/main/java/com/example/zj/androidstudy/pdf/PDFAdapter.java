package com.example.zj.androidstudy.pdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zj.androidstudy.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019-12-24.
 */
//public class PDFAdapter extends BaseQuickAdapter<PDFFileInfo, BaseViewHolder> {
//  public PDFAdapter(@Nullable List<PDFFileInfo> data) {
//    super(R.layout.layout_item_pdf,data);
//  }
//  @Override
//  protected void convert(BaseViewHolder helper, PDFFileInfo item) {
//    if(item == null){
//      return;
//    }
//    helper.setText(R.id.tv_name , item.getFileName());
//    helper.setText(R.id.tv_size , PDFUtil.FormetFileSize(item.getFileSize()));
//    helper.setText(R.id.tv_time , item.getTime());
//    if (item.isSelect()){
//      helper.getView(R.id.img_select).setBackgroundResource(R.mipmap.reward_selection_ok);
//    }else {
//      helper.getView(R.id.img_select).setBackgroundResource(R.mipmap.reward_selection_no);
//    }
//  }
//}
public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.ViewHolder> {
  private Context context;
  private List<PDFFileInfo> fileInfoList;
  private OnItemClickListener itemClickListener;

  public PDFAdapter(Context context, List<PDFFileInfo> pdfFileInfos) {
    this.context = context;
    this.fileInfoList = pdfFileInfos;
  }

  @NonNull
  @Override
  public PDFAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.layout_item_pdf, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull PDFAdapter.ViewHolder holder, final int position) {
    PDFFileInfo fileInfo = fileInfoList.get(position);
    holder.tvFileName.setText(fileInfo.getFileName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (itemClickListener != null) {
          itemClickListener.onItemClick(position);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return fileInfoList != null ? fileInfoList.size() : 0;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvFileName;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvFileName = itemView.findViewById(R.id.tv_name);
    }
  }

  public void setOnItemClickListener(OnItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public interface OnItemClickListener{
    void onItemClick(int position);
  }
}