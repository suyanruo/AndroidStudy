package com.example.zj.androidstudy.picture

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.zj.androidstudy.Constants
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.adapter.PictureAdapter
import com.example.zj.androidstudy.model.PictureInfo
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 显示所有相册图片
 */
class PictureActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "PictureActivity"
    }

    private val pictureList = mutableListOf<PictureInfo>()

    private lateinit var rvPicture: RecyclerView
    private lateinit var adapter: PictureAdapter

    private lateinit var workRequest: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        pictureList.addAll(ImageHelper.getPictures())
        initRecyclerView()
        if (pictureList.isNullOrEmpty()) {
            initWorker()
            if (checkStoragePermission()) {
//            startFetch()
                startWorker()
            }
        }
    }

    private fun initRecyclerView() {
        rvPicture = findViewById(R.id.rv_pictures)
        rvPicture.layoutManager = LinearLayoutManager(this)
        adapter = PictureAdapter(this, pictureList)
        rvPicture.adapter = adapter
    }

    private fun checkStoragePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.REQUEST_PERMISSION_CODE)
            return false
        }
        return true
    }

    private fun initWorker() {
        // 构建request方法一
//        workRequest = OneTimeWorkRequest.from(ImageScanWorker::class.java)
        // 构建request方法二
        val constraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build()
        workRequest = OneTimeWorkRequestBuilder<ImageScanWorker>()
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setInputData(workDataOf("IMAGE_URI" to "https://www.baidu.com"))
                .build()
    }

    /**
     * 使用WorkManager开启耗时任务
     */
    private fun startWorker() {
//        WorkManager.getInstance(this).enqueue(workRequest)

        WorkManager.getInstance(this).enqueueUniqueWork(
                "scanImage",
                ExistingWorkPolicy.KEEP,
                workRequest as OneTimeWorkRequest
        )

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id)
                .observe(this, Observer<WorkInfo> { workInfo ->
                    if (workInfo?.state == WorkInfo.State.RUNNING) {
                        Log.e("Track", "progress: " + workInfo.progress.getInt("progress", 0))
                    } else if(workInfo?.state == WorkInfo.State.SUCCEEDED) {
                        Log.e("Track", "success")
                        Snackbar.make(rvPicture, "work completed", Snackbar.LENGTH_SHORT).show()
                        refreshView()
                    }
                })
    }


    /**
     * 开启子线程获取图片信息
     */
    private fun startFetch() {
        Log.e(TAG, "startFetch" )
        Observable.create(ObservableOnSubscribe() { e: ObservableEmitter<List<PictureInfo>> ->
            ImageHelper.fetchPicture(this@PictureActivity)
            e.onNext(pictureList)
            e.onComplete()
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { refreshView() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startFetch()
                startWorker()
            }
        }
    }

    private fun refreshView() {
        val pictureInfo = ImageHelper.getPictures()
        if (!pictureInfo.isNullOrEmpty()) {
            pictureList.clear()
            pictureList.addAll(pictureInfo)
            adapter.notifyDataSetChanged()
        }
    }
}