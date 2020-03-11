package com.example.zj.androidstudy.pdf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.PDFUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 搜索手机存储中所有的pdf文件
 * 参考：https://www.okcode.net/article/48697
 */
public class PdfSearchActivity extends AppCompatActivity {
  private RecyclerView mRecyclerView;
  private ProgressDialog progressDialog;
  private PDFAdapter pdfAdapter;
  private ImageView imgBack;
  private TextView tvTitle;
  private TextView tvFinish;
  private ArrayList<PDFFileInfo> pdfData = new ArrayList<>();
  private String TAG = PdfSearchActivity.class.getSimpleName();
  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 1) {
        initRecyclerView();
      }
    }
  };
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pdf_search);
    initViews();
    initListener();
  }
  private void initViews() {
    mRecyclerView = findViewById(R.id.rv_pdf);
//    imgBack = findViewById(R.id.iv_back);
//    tvTitle = findViewById(R.id.tv_title);
//    tvFinish = findViewById(R.id.tv_right);
//    tvTitle.setText("PDF文件搜索");
//    imgBack.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        finish();
//      }
//    });
    showDialog();
    new Thread() {
      @Override
      public void run() {
        super.run();
        getFolderData();
      }
    }.start();
  }
  private void initListener() {
  }
  private void showDialog() {
    progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
    progressDialog.setMessage("正在加载数据中...");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
  }
  private void initRecyclerView() {
    pdfAdapter = new PDFAdapter(this, pdfData);
    View notDataView = getLayoutInflater().inflate(R.layout.layout_pdf_empty_view, (ViewGroup) mRecyclerView.getParent(), false);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(pdfAdapter);
    pdfAdapter.setOnItemClickListener(new PDFAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        if (!pdfData.get(position).isSelect()) {
          if (getSelectNumber() >= 3) {
            Toast.makeText(PdfSearchActivity.this, "最多可选择3个文件", Toast.LENGTH_SHORT).show();
            return;
          }
        }
        for (int i = 0; i < pdfData.size(); i++) {
          if (i == position) {
            if (pdfData.get(i).isSelect()) {
              pdfData.get(i).setSelect(false);
            } else {
              pdfData.get(i).setSelect(true);
            }
          }
        }
        pdfAdapter.notifyDataSetChanged();
        tvFinish.setText("完成(" + getSelectNumber() + ")");
      }
    });
//    if (pdfData != null && pdfData.size() > 0) {
//      for (int i = 0; i < pdfData.size(); i++) {
//        pdfData.get(i).setSelect(false);
//      }
//      pdfAdapter.setNewData(pdfData);
//    } else {
//      pdfAdapter.setEmptyView(notDataView);
//    }
    progressDialog.dismiss();
  }
  /**
   * 遍历文件夹中资源
   */
  public void getFolderData() {
    getDocumentData();
    handler.sendEmptyMessage(1);
  }
  /**
   * 获取手机文档数据
   *
   * @param
   */
  public void getDocumentData() {
    String[] columns = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.DATA};
    String select = "(_data LIKE '%.pdf')";
    ContentResolver contentResolver = getContentResolver();
    Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, null, null);
    int columnIndexOrThrow_DATA = 0;
    if (cursor != null) {
      columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
    }
    if (cursor != null) {
      while (cursor.moveToNext()) {
        String path = cursor.getString(columnIndexOrThrow_DATA);
        PDFFileInfo document = PDFUtil.getFileInfoFromFile(new File(path));
        pdfData.add(document);
        Log.d(TAG, " pdf " + document);
      }
    }
    cursor.close();
  }
  private int getSelectNumber() {
    int k = 0;
    for (int i = 0; i < pdfData.size(); i++) {
      if (pdfData.get(i).isSelect()) {
        k++;
      }
    }
    return k;
  }
}
