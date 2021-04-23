package com.example.zj.androidstudy.model

import android.net.Uri

/**
 * Created on 4/22/21.
 *  相册图片相关信息
 */
data class PictureInfo(val uri: Uri, val name: String, val size: Int)