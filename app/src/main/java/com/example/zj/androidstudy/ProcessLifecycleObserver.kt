package com.example.zj.androidstudy

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.zj.androidstudy.tool.ProcessUtil

/**
 * 监听app是否在前台
 */
class ProcessLifecycleObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppForeground() {
        ProcessUtil.isAppInBackground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppBackground() {
        ProcessUtil.isAppInBackground = true
    }
}