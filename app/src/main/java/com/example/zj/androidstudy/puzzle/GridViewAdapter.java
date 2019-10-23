package com.example.zj.androidstudy.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.zj.androidstudy.tool.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> picList = new ArrayList<>();

    public GridViewAdapter(Context context, List<Bitmap> picList) {
        this.context = context;
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView picItem;
        int density = (int) ScreenUtil.getDeviceDensity(context);
        if (convertView == null) {
            picItem = new ImageView(context);
            picItem.setLayoutParams(new GridLayoutManager.LayoutParams(80 * density, 100 * density));
            picItem.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            picItem = (ImageView) convertView;
        }
        picItem.setBackgroundColor(context.getResources().getColor(android.R.color.black));
        picItem.setImageBitmap(picList.get(position));
        return picItem;
    }
}
