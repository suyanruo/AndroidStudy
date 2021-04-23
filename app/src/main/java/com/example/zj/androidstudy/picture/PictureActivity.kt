package com.example.zj.androidstudy.picture

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zj.androidstudy.Constants
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.adapter.PictureAdapter
import com.example.zj.androidstudy.model.PictureInfo
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 显示所有相册图片
 */
class PictureActivity : AppCompatActivity() {
    private val pictureList = mutableListOf<PictureInfo>()
    private val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
    )
//    private val selection = "${MediaStore.Images.Media.MIME_TYPE} = ?";
//    private val selectionArgs = arrayOf("image/jpeg")
    private val sort = "${MediaStore.Images.Media.DATE_MODIFIED} DESC";

    private lateinit var rvPicture: RecyclerView
    private lateinit var adapter: PictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        initRecyclerView()
        if (checkStoragePermission()) {
            startFetch()
        }
    }

    private fun initRecyclerView() {
        rvPicture = findViewById(R.id.rv_pictures)
        rvPicture.layoutManager = LinearLayoutManager(this)
        adapter = PictureAdapter(this, pictureList)
        rvPicture.adapter = adapter
    }

    private fun startFetch() {
        Log.e(Companion.TAG, "startFetch" )
        Observable.create(ObservableOnSubscribe() { e: ObservableEmitter<List<PictureInfo>> ->
            fetchPicture()
            e.onNext(pictureList)
            e.onComplete()
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { refreshView() }
    }

    private fun checkStoragePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.REQUEST_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startFetch()
            }
        }
    }

    private fun fetchPicture() {
        val query = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sort)
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            Log.e(TAG, "cursor.count:" + cursor.count)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val contentUri: Uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                pictureList += PictureInfo(contentUri, name, size)
            }
        }
    }

    private fun refreshView() {
        Log.e(TAG, "refreshView picture size:" + pictureList.size)
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "PictureActivity"
    }
}