package com.example.zj.androidstudy.zxing

import android.os.Bundle
import android.widget.Toast
import com.example.zj.androidstudy.R
import com.king.zxing.camera.FrontLightMode

class ZXingActivity : CaptureActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCameraScan()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_zxing
    }

    private fun initCameraScan() {
        //获取CaptureHelper，里面有扫码相关的配置设置
        captureHelper.playBeep(true) // 播放音效
                .vibrate(false) // 震动
                .supportVerticalCode(true) // 支持扫垂直条码，建议有此需求时才使用。
//                .fullScreenScan(false) // 全屏扫码
//                .decodeFormats(DecodeFormatManager.QR_CODE_FORMATS)//设置只识别二维码会提升速度
//                .framingRectRatio(0.9f) // 设置识别区域比例，范围建议在0.625 ~ 1.0之间。非全屏识别时才有效
//                .framingRectVerticalOffset(0) // 设置识别区域垂直方向偏移量，非全屏识别时才有效
//                .framingRectHorizontalOffset(0) // 设置识别区域水平方向偏移量，非全屏识别时才有效
                .frontLightMode(FrontLightMode.AUTO) // 设置闪光灯模式
                .tooDarkLux(45f) // 设置光线太暗时，自动触发开启闪光灯的照度值
                .brightEnoughLux(100f) // 设置光线足够明亮时，自动触发关闭闪光灯的照度值
                .continuousScan(false) // 是否连扫
                .supportLuminanceInvert(true) // 是否支持识别反色码（黑白反色的码），增加识别率
                .supportAutoZoom(false) // 是否自动缩放


    }

    override fun onResultCallback(result: String?): Boolean {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        return super.onResultCallback(result)
    }
}