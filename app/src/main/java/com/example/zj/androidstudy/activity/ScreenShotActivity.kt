package com.example.zj.androidstudy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.adapter.RecyclerViewAdapter
import com.example.zj.androidstudy.tool.ShotUtil
import com.example.zj.androidstudy.tool.StorageUtil

class ScreenShotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_shot)

        initView()
    }

    private fun initView() {
        val clRoot = findViewById<ConstraintLayout>(R.id.cl_shot_root)
        val tvContent = findViewById<TextView>(R.id.tv_shot_content)
        val scrollView = findViewById<NestedScrollView>(R.id.nsv_shot)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shot)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(this)

        findViewById<Button>(R.id.btn_shot_activity).setOnClickListener {
            val bitmap = ShotUtil.getBitmap(this)
            StorageUtil.saveImage(this, bitmap)
        }
        findViewById<Button>(R.id.btn_shot_root_view).setOnClickListener {
            val bitmap = ShotUtil.shotView(clRoot, ContextCompat.getColor(this, android.R.color.white))
            StorageUtil.saveImage(this, bitmap)
        }
        findViewById<Button>(R.id.btn_shot_content).setOnClickListener {
            ShotUtil.shotView(tvContent, this) {
                StorageUtil.saveImage(this, it)
            }
        }
        findViewById<Button>(R.id.btn_shot_scrollview).setOnClickListener {
            val bitmap = ShotUtil.shotScrollView(scrollView)
            StorageUtil.saveImage(this, bitmap)
        }
        findViewById<Button>(R.id.btn_shot_scrollview).setOnClickListener {
            val bitmap = ShotUtil.shotScrollView(scrollView)
            StorageUtil.saveImage(this, bitmap)
        }
        findViewById<Button>(R.id.btn_shot_recyclerview).setOnClickListener {
            val bitmap = ShotUtil.shotRecyclerView(recyclerView)
            StorageUtil.saveImage(this, bitmap)
        }
    }
}