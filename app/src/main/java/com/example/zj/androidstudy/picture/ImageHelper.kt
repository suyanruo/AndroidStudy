package com.example.zj.androidstudy.picture

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.example.zj.androidstudy.model.PictureInfo

/**
 * Created on 4/23/21.
 *
 */
object ImageHelper {
    private val pictureList = mutableListOf<PictureInfo>()

    private val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
    )
//    private val selection = "${MediaStore.Images.Media.MIME_TYPE} = ?";
//    private val selectionArgs = arrayOf("image/jpeg")
    private val sort = "${MediaStore.Images.Media.DATE_MODIFIED} DESC";

    fun getPictures(): List<PictureInfo> {
        return pictureList
    }

    fun fetchPicture(context: Context): List<PictureInfo> {
//        val pictureList = mutableListOf<PictureInfo>()
        pictureList.clear()
        Log.e("Track", "fetchPicture")
        val query = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sort)
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                var contentUri: Uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // 获取图片位置信息
                    contentUri = MediaStore.setRequireOriginal(contentUri)
                    context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                        ExifInterface(inputStream).run {
                            val latLong = latLong ?: doubleArrayOf(0.0, 0.0)
                            Log.e("ImageHelper", "lat: " + latLong[0] + "  " + latLong[1])
                        }
                    }

                }
                pictureList += PictureInfo(contentUri, name, size)
                Log.e("ImageHelper", "picture name:" + name + ", size:" + size)
            }
        }
        return pictureList
    }
}