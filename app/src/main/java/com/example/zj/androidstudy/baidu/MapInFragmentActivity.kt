package com.example.zj.androidstudy.baidu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baidu.mapapi.map.LogoPosition
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.SupportMapFragment
import com.baidu.mapapi.model.LatLng
import com.example.zj.androidstudy.R


class MapInFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_in_fragment)

        initView()
    }

    private fun initView() {
        val GEO_BEIJING = LatLng(39.945, 116.404)

        //北京为地图中心，logo在左上角
        val status1 = MapStatusUpdateFactory.newLatLng(GEO_BEIJING)
        val map1 = supportFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
        map1.baiduMap.setMapStatus(status1)
        map1.mapView.logoPosition = LogoPosition.logoPostionleftTop
    }
}