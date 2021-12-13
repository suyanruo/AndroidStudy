package com.example.zj.androidstudy.baidu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.zj.androidstudy.R

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        findViewById<Button>(R.id.btn_map_view).setOnClickListener {
            startActivity(Intent(this, MapInViewActivity::class.java))
        }
        findViewById<Button>(R.id.btn_map_fragment).setOnClickListener {
            startActivity(Intent(this, MapInFragmentActivity::class.java))
        }
    }
}