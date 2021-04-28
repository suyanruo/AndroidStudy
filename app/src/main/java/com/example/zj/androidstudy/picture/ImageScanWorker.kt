package com.example.zj.androidstudy.picture

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson

/**
 * Created on 4/23/21.
 *
 */
class ImageScanWorker(var context: Context, workerParams: WorkerParameters) :
        Worker(context, workerParams) {
    override fun doWork(): Result {
        // Do the work here
        // 获取传入参数
        setProgressAsync(workDataOf(Progress to 0))
        val imageUri = inputData.getString("IMAGE_URI") ?: return Result.failure()
        if (isStopped) {
            return Result.failure()
        }
        val imageInfo = ImageHelper.fetchPicture(context)
        setProgressAsync(Data.Builder().putInt(Progress, 100).build())
        // Indicate whether the work finished successfully with the Result
        return Result.success(Data.Builder().let {
            it.putString("IMAGE_INFO", Gson().toJson(imageInfo))
            it.build()
        })
    }

    override fun onStopped() {
        super.onStopped()
        // worker被取消后如果此worker还没执行完成，此时会调用此方法
    }

    companion object {
        const val Progress = "Progress"
    }
}