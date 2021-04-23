package com.example.zj.androidstudy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.model.PictureInfo

/**
 * Created on 4/22/21.
 * 显示相册图片
 */
class PictureAdapter(private val context: Context, private val dataList: List<PictureInfo>?) : RecyclerView.Adapter<PictureAdapter.PictureHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false)
        return PictureHolder(context, view)
    }

    override fun onBindViewHolder(holder: PictureHolder, position: Int) {
        dataList?.get(position)?.let { holder.refresh(it) }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    class PictureHolder(@JvmField var context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        private val ivPicture = itemView.findViewById<ImageView>(R.id.iv_picture)

        fun refresh(info: PictureInfo) {
            tvName.text = info.name
            Glide.with(context).load(info.uri).into(ivPicture)
        }
    }
}